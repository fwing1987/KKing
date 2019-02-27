<template>
  <div>
    <a-card style="margin-bottom:10px">
      <a-form layout="inline">
        <a-row :gutter="16" type="flex" justify="start">
          <a-col :span="6">
            <a-form-item label="名称：">
              <a-input type="text" v-model="params.roleDesc"> </a-input>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="标识：">
              <a-input type="text" v-model="params.roleName"> </a-input>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="类型：">
              <a-radio-group v-model="params.state">
                <a-radio-button value="0">正常</a-radio-button>
                <a-radio-button value="1">禁用</a-radio-button>
              </a-radio-group>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item>
              <a-button type="primary" @click="getData"> <a-icon type="search"></a-icon>搜索 </a-button>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-card>
    <a-card hoverable>
      <a-button-group>
        <a-button
          type="primary"
          :disabled="tableSelected.type && tableSelected.type === 'B'"
          @click="addRoleClick"
          v-action:add
        >
          <a-icon type="plus" />新增
        </a-button>
        <a-button type="primary" @click="editRoleClick" v-action:edit> <a-icon type="edit" />修改 </a-button>
        <a-button type="danger" @click="deleteRoleClick" v-action:remove> <a-icon type="delete" />删除 </a-button>
      </a-button-group>
      <a-table
        style="margin-top:10px"
        :columns="header"
        :dataSource="data"
        rowKey="id"
        :rowSelection="rowSelection"
        :customRow="customRow"
        :pagination="false"
      ></a-table>
    </a-card>

    <a-modal v-model="showMenuModal" destroyOnClose @ok="editPermssion" :confirmLoading="menuTree.menuModalLoading">
      <a-row>
        <a-col :span="12">
          <a-card title="所有菜单">
            <a-tree
              :treeData="menuTree.menuData"
              showIcon
              defaultExpandAll
              checkable
              showLine
              @check="checkTreeMenu"
              :defaultCheckedKeys="menuTree.selectedTreeKeys"
            />
          </a-card>
        </a-col>
        <a-col :span="12">
          <a-card title="已分配菜单">
            <a-tree
              :treeData="menuTree.selectedMenuData"
              showIcon
              defaultExpandAll
            />
          </a-card>
        </a-col>
      </a-row>
    </a-modal>

    <a-modal v-model="showRoleModal" title="角色修改" :maskClosable="false" @ok="editRole">
      <a-form :form="form">
        <a-form-item v-show="false">
          <a-input type="hidden" v-decorator="['id']"></a-input>
        </a-form-item>

        <a-form-item label="名称：" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-input type="text" v-decorator="['roleDesc', { rules: [{ required: true, message: '请输入角色名' }] }]">
          </a-input>
        </a-form-item>
        <a-form-item label="标识：" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-input
            type="text"
            v-decorator="['roleName', { rules: [{ required: true, message: '请输入标识名' }] }]"
          >
          </a-input>
        </a-form-item>
        <a-form-item label="状态：" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-switch
            v-decorator="['state', { valuePropName: 'checked' }]"
          >
          </a-switch>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>
<script>
import { getRoleList, addRole, updateRole, deleteRole, editRolePermssion } from '@/api/role'
import { getMenuList } from '@/api/menu'

export default {
  methods: {
    makeSelectedMenuData (data, selectedKeys, e) {
      // 选出已选择的菜单
      var ret = null
      var childRet
      for (var i = 0; i < data.length; i++) {
        if (data[i].children) {
          childRet = this.makeSelectedMenuData(data[i].children, selectedKeys, e)
        }
        if (childRet || selectedKeys.indexOf(data[i].id) >= 0) {
          if (!ret) ret = []
          var newData = {
            title: data[i].title,
            key: data[i].key,
            icon: data[i].icon
          }
          if (childRet) {
            newData.children = childRet
          }
          ret.push(newData)
        }
        if (data[i].actionName && e) {
          // 将有变化的节点变色突出展示
          if (selectedKeys.indexOf(data[i].id) >= 0 && !data[i].roleId ||
               selectedKeys.indexOf(data[i].id) < 0 && data[i].roleId) {
            data[i].class = 'tree-selected-node'
            this.menuTree.changeMenuData.push({ permId: data[i].permId, new: !data[i].roleId })
          } else {
            data[i].class = ''
          }
        }
      }
      return ret
    },
    checkTreeMenu (checkedKeys, e) {
      // 菜单树选择change
      this.menuTree.changeMenuData = []
      if (e && e.halfCheckedKeys) {
        this.menuTree.selectedMenuData = this.makeSelectedMenuData(this.menuTree.menuData, checkedKeys.concat(e.halfCheckedKeys), e)
      } else {
        this.menuTree.selectedMenuData = this.makeSelectedMenuData(this.menuTree.menuData, checkedKeys, e)
      }
    },
    customRow (record) {
      // table点击事件
      return {
        on: {
          click: () => {
            this.rowSelection.selectedRowKeys = [record.id]
            this.tableSelected = record
          }
        }
      }
    },
    initRoleParams () {
      this.menuParams = {
        state: 1,
        roleDesc: '',
        roleName: ''
      }
    },
    addRoleClick () {
      this.editType = 'add'
      this.initRoleParams()
      this.showRoleModal = true
    },
    editRoleClick () {
      if (!this.tableSelected.id) {
        this.$message.warning('请选择要修改的角色项', 5)
        return
      }
      this.editType = 'update'
      this.initRoleParams()
      _.merge(this.menuParams, this.tableSelected)
      this.menuParams.state = !this.menuParams.state
      this.showRoleModal = true
    },
    deleteRoleClick () {
      if (!this.tableSelected.id) {
        this.$message.warning('请选择要删除的角色项', 5)
        return
      }
      this.$confirm({
        title: `确认删除角色【${this.tableSelected.roleDesc}】吗？`,
        onOk: () => {
          deleteRole(this.tableSelected.id).then(res => {
            if (!res.code) {
              this.tableSelected = {}
              this.$message.success('删除成功', 5)
              this.getData()
            } else {
              this.$error(`删除失败：${res.msg}`, 5)
            }
          })
        }
      })
    },
    editRole () {
      this.form.validateFields((err, values) => {
        if (!err) {
          values.state = !values.state
          if (this.editType === 'add') {
            addRole(values).then(res => {
              if (!res.code) {
                this.showRoleModal = false
                this.$message.success('添加成功', 5)
                this.getData()
              } else {
                this.$error(`添加失败：${res.msg}`, 5)
              }
            })
          } else if (this.editType === 'update') {
            updateRole(values).then(res => {
              if (!res.code) {
                this.showRoleModal = false
                this.$message.success('修改成功', 5)
                this.getData()
              } else {
                this.$error(`修改失败：${res.msg}`, 5)
              }
            })
          }
        }
      })
    },
    editPermssion () {
      if (!this.tableSelected.id) {
        this.$error(`请选择要修改的角色`, 5)
        return
      } else if (this.menuTree.changeMenuData.length === 0) {
        this.showMenuModal = false
        return
      }
      this.menuTree.menuModalLoading = true
      var params = {
        'permList': this.menuTree.changeMenuData,
        id: this.tableSelected.id
      }
      editRolePermssion(params).then(res => {
        this.menuTree.menuModalLoading = false
        if (!res.code) {
          this.showMenuModal = false
          this.$message.success('修改权限成功', 5)
        } else {
          this.$error(`修改权限失败：${res.msg}`, 5)
        }
      })
    },
    getData () {
      getRoleList(this.params).then(res => {
        this.data = res.data.map(item => {
          item.checked = item.state === 0// 正常状态state==0转换为开关状态1
          return item
        })
      })
    },
    makeTreeDataSafe (data) {
      // 修改为antd控件需要的不同属性名
      for (var i = 0; i < data.length; i++) {
        data[i].title = data[i].name
        data[i].key = data[i].id
        if (data[i].icon) {
          data[i].icon = (<a-icon type={data[i].icon} />)
        }
        if (data[i].children) {
          this.makeTreeDataSafe(data[i].children)
        }
      }
    },
    roleStateChange (checked, row) {
      // 修改角色状态
      row.state = !checked
      var msg = checked ? '启用' : '停用'
      this.$confirm({
        title: `确认要${msg}角色【${row.roleDesc}】吗？`,
        onOk: () => {
          updateRole(row).then(res => {
            if (!res.code) {
              this.$message.success('修改成功', 5)
              this.getData()
            } else {
              this.$error(`修改失败：${res.msg}`, 5)
            }
          })
        }
      })
    },
    getSelectedTreeKeys (data) {
      // 获取角色菜单初始选择信息
      for (var i = 0; i < data.length; i++) {
        if (data[i].children) {
          this.getSelectedTreeKeys(data[i].children)
        } else {
          if (data[i].roleId) {
            this.menuTree.selectedTreeKeys.push(data[i].id)
          }
        }
      }
    }
  },
  created () {
    this.getData()
  },
  watch: {
    showMenuModal (newVal) {
      if (newVal) {
        // 设置成{}才会默认展开，[]默认全部收起，奇怪？？
        this.menuTree.menuData = {}
        this.menuTree.selectedTreeKeys = {}
        getMenuList({ roleId: this.tableSelected.id }).then(res => {
          this.menuTree.menuData = res.data.menus
          this.menuTree.selectedTreeKeys = []
          this.getSelectedTreeKeys(this.menuTree.menuData)
          this.makeTreeDataSafe(this.menuTree.menuData)
          this.checkTreeMenu(this.menuTree.selectedTreeKeys)
        })
      }
    },
    showRoleModal (newVal) {
      if (newVal) {
        this.$nextTick(() => {
          this.form.setFieldsValue(this.menuParams)
        })
      }
    }
  },
  data () {
    return {
      menuTree: {
        menuData: {},
        selectedMenuData: {},
        selectedTreeKeys: [],
        changeMenuData: [], // 权限选择变化过的节点
        menuModalLoading: false // 修改权限弹出框，确认按钮loading
      },
      labelCol: {
        xs: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 17 }
      },
      form: this.$form.createForm(this),
      rowSelection: {
        type: 'radio',
        selectedRowKeys: []
      },
      header: [
        {
          title: '角色编号',
          dataIndex: 'id'
        },
        {
          title: '名称',
          dataIndex: 'roleDesc'
        },
        {
          title: '标识',
          dataIndex: 'roleName'
        },
        {
          title: '状态',
          dataIndex: 'state',
          customRender: (state, row, index) => {
            return (
              <div>
                {this.$hasAction('edit') ? (
                  <a-switch checked={row.checked} onChange={(checked) => { this.roleStateChange(checked, row) }}/>
                ) : (
                  <div>{state === 0 ? <a-tag color="cyan">正常</a-tag> : <a-tag color="red">禁用</a-tag>}</div>
                )}
              </div>
            )
          }
        },
        {
          title: '创建时间',
          dataIndex: 'createTime'
        },
        {
          title: '操作',
          align: 'center',
          customRender: (state, row, index) => {
            return (
              <div>
                <a-button-group>
                  {this.$hasAction('edit') ? (
                    <a-tooltip title="修改">
                      <a-button
                        type="primary"
                        size="small"
                        icon="edit"
                        onClick={() => {
                          this.tableSelected = row
                          this.editRoleClick()
                        }}
                      >
                      修改
                      </a-button>
                    </a-tooltip>
                  ) : (
                    ''
                  )}
                  {this.$hasAction('edit') ? (
                    <a-tooltip title="权限分配">
                      <a-button
                        type="primary"
                        size="small"
                        icon="solution"
                        onClick={() => {
                          this.tableSelected = row
                          this.showMenuModal = true
                        }}
                      >
                      权限分配
                      </a-button>
                    </a-tooltip>
                  ) : (
                    ''
                  )}
                  {this.$hasAction('remove') ? (
                    <a-tooltip title="删除">
                      <a-button
                        type="danger"
                        size="small"
                        icon="delete"
                        onClick={() => {
                          this.tableSelected = row
                          this.deleteRoleClick()
                        }}
                      >
                      删除
                      </a-button>
                    </a-tooltip>
                  ) : (
                    ''
                  )}
                </a-button-group>
              </div>
            )
          }
        }
      ],
      data: [],
      params: {
        name: '',
        type: ''
      },
      menuParams: {
        type: 'M'
      },
      showMenuModal: false,
      showRoleModal: false,
      tableSelected: {},
      editType: ''
    }
  }
}
</script>
<style>
.ant-card-body {
  padding: 10px;
}
.ant-card-wider-padding .ant-card-body {
  padding: 10px;
}
.ant-table-tbody > tr > td {
  padding: 10px 10px;
}
.tree-selected-node .ant-tree-title{
  color: red
}
.tree-selected-node i{
  color: red
}
</style>
