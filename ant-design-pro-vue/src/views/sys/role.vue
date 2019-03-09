<template>
  <div>
    <a-card style="margin-bottom:10px" v-perm:role:list>
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
      <a-spin tip="加载中，请稍候..." :spinning="loading">
        <a-button-group>
          <a-button
            type="primary"
            :disabled="tableSelected.type && tableSelected.type === 'B'"
            @click="addRoleClick"
            v-perm:role:add
          >
            <a-icon type="plus" />新增
          </a-button>
          <a-button type="primary" @click="editRoleClick" v-perm:role:edit> <a-icon type="edit" />修改 </a-button>
          <a-button type="danger" @click="deleteRoleClick" v-perm:role:remove> <a-icon type="delete" />删除 </a-button>
        </a-button-group>
        <a-table
          style="margin-top:10px"
          :columns="header"
          :dataSource="data"
          rowKey="id"
          :rowSelection="rowSelection"
          :customRow="customRow"
          :pagination="false"
        />
      </a-spin>
    </a-card>

    <a-modal v-model="showMenuModal" destroyOnClose @ok="editPermssion" :confirmLoading="modalTree.menuModalLoading">
      <a-spin tips="加载中，请稍候..." :spinning="modalTree.loading">
        <a-row>
          <a-col :span="12">
            <a-card title="所有资源">
              <a-tree
                :treeData="modalTree.treeData"
                showIcon
                checkable
                showLine
                @check="checkTreeMenu"
                @expand="expandTreeMenu"
                :checkStrictly="modalTree.checkStrictly"
                :defaultCheckedKeys="modalTree.selectedTreeKeys"
                :defaultExpandedKeys="modalTree.defaultExpandedKeys"
              />
            </a-card>
          </a-col>
          <a-col :span="12">
            <a-card title="已分配资源">
              <a-tree
                :treeData="modalTree.selectedMenuData"
                showIcon
                :expandedKeys="modalTree.defaultExpandedKeys"
              />
            </a-card>
          </a-col>
        </a-row>
      </a-spin>
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
import { getRoleList, addRole, updateRole, deleteRole, editRolePermssion, editRoleDept } from '@/api/role'
import { getMenuWithRoleStatus } from '@/api/menu'
import { getDeptWithRoleStatus } from '@/api/dept'

export default {
  methods: {
    makeSelectedMenuData (data, selectedKeys, e) {
      // 选出已选择的菜单
      var ret = null
      for (var i = 0; i < data.length; i++) {
        var childRet = null
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
        if (e) {
          // 将有变化的节点变色突出展示
          if (selectedKeys.indexOf(data[i].id) >= 0 && !data[i].roleId ||
                selectedKeys.indexOf(data[i].id) < 0 && data[i].roleId) {
            data[i].class = 'tree-selected-node'
            this.modalTree.changeMenuData.push({ permId: data[i].permId, new: !data[i].roleId })
          } else {
            data[i].class = ''
          }
        }
      }
      return ret
    },
    makeSelectedDeptData (data, checkedKeys, disableCheck) {
      // 部门因为权限继承，有父部门的权限就有子部门的权限
      // 所以在父节点选择的情况下，下级部门是否选择就没有什么关系，直接禁用
      for (let i = 0; i < data.length; i++) {
        data[i].disableCheckbox = disableCheck
        data[i].disabled = disableCheck
        if (!disableCheck && (checkedKeys.indexOf(data[i].id) >= 0 && !data[i].roleId ||
          checkedKeys.indexOf(data[i].id) < 0 && data[i].roleId)) {
          data[i].class = 'tree-selected-node'
        } else {
          data[i].class = ''
        }
      }
      var ret = null
      for (let i = 0; i < data.length; i++) {
        var childRet = null
        if (data[i].children) {
          childRet = this.makeSelectedDeptData(data[i].children, checkedKeys, disableCheck || checkedKeys.indexOf(data[i].id) >= 0)
        }
        if (!disableCheck) {
          if (!ret) ret = []
          var item = { id: data[i].id, check: checkedKeys.indexOf(data[i].id) >= 0 }
          if (childRet) {
            item['children'] = childRet
          }
          ret.push(item)

          if (checkedKeys.indexOf(data[i].id) >= 0) {
            this.modalTree.selectedMenuData.push(data[i])
          }
        }
      }
      return ret
    },
    checkTreeMenu (checkedKeys, e) {
      // 菜单树选择change
      if (checkedKeys.checked) {
        checkedKeys = checkedKeys.checked
      }
      this.modalTree.changeMenuData = []
      this.modalTree.selectedMenuData = []
      if (this.permEditType === 'dept') {
        this.modalTree.changeMenuData = this.makeSelectedDeptData(this.modalTree.treeData, checkedKeys, false)
      } else if (this.permEditType === 'menu') {
        if (e && e.halfCheckedKeys) {
          this.modalTree.selectedMenuData = this.makeSelectedMenuData(this.modalTree.treeData, checkedKeys.concat(e.halfCheckedKeys), e)
        } else {
          this.modalTree.selectedMenuData = this.makeSelectedMenuData(this.modalTree.treeData, checkedKeys, e)
        }
      }
    },
    expandTreeMenu (expandedKeys, e) {
      this.modalTree.defaultExpandedKeys = expandedKeys
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
      this.modalParams = {
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
      Object.assign(this.modalParams, this.tableSelected)
      this.modalParams.state = !this.modalParams.state
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
              this.$error({ title: `删除失败：${res.msg}` })
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
                this.$error({ title: `添加失败：${res.msg}` })
              }
            })
          } else if (this.editType === 'update') {
            updateRole(values).then(res => {
              if (!res.code) {
                this.showRoleModal = false
                this.$message.success('修改成功', 5)
                this.getData()
              } else {
                this.$error({ title: `修改失败：${res.msg}` })
              }
            })
          }
        }
      })
    },
    editPermssion () {
      if (!this.tableSelected.id) {
        this.$error({ title: `请选择要修改的角色` })
        return
      } else if (this.modalTree.changeMenuData.length === 0) {
        this.showMenuModal = false
        return
      }
      this.modalTree.menuModalLoading = true
      var params = {
        'changeList': this.modalTree.changeMenuData,
        id: this.tableSelected.id
      }

      var editFunc
      if (this.permEditType === 'dept') {
        editFunc = editRoleDept
      } else if (this.permEditType === 'menu') {
        editFunc = editRolePermssion
      }
      editFunc(params).then(res => {
        this.modalTree.menuModalLoading = false
        if (!res.code) {
          this.showMenuModal = false
          this.$message.success('修改权限成功', 5)
        } else {
          this.$error({ title: `修改权限失败：${res.msg}` })
        }
      })
    },
    getData () {
      this.loading = true
      getRoleList(this.params).then(res => {
        this.loading = false
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
        data[i].disableCheckbox = false
        data[i].disabled = false
        data[i].class = ''
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
              this.$error({ title: `修改失败：${res.msg}` })
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
        }
        if (this.permEditType === 'menu' && !data[i].children ||
          this.permEditType === 'dept') {
          if (data[i].roleId) {
            this.modalTree.selectedTreeKeys.push(data[i].id)
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
        var getListFunc
        if (this.permEditType === 'dept') {
          getListFunc = getDeptWithRoleStatus
          this.modalTree.checkStrictly = true
        } else if (this.permEditType === 'menu') {
          getListFunc = getMenuWithRoleStatus
          this.modalTree.checkStrictly = false
        }
        // 设置成{}才会默认展开，[]默认全部收起，奇怪？？
        this.modalTree.treeData = {}
        this.modalTree.selectedTreeKeys = {}
        this.modalTree.loading = true
        getListFunc({ roleId: this.tableSelected.id }).then(res => {
          this.modalTree.loading = false
          this.makeTreeDataSafe(res.data)
          this.$set(this.modalTree, 'treeData', res.data)
          this.modalTree.selectedTreeKeys = []
          this.getSelectedTreeKeys(this.modalTree.treeData)
          this.checkTreeMenu(this.modalTree.selectedTreeKeys)
          this.modalTree.defaultExpandedKeys = this.modalTree.treeData.map(item => item.id)
        })
      }
    },
    showRoleModal (newVal) {
      if (newVal) {
        this.$nextTick(() => {
          this.form.setFieldsValue(this.modalParams)
        })
      }
    }
  },
  data () {
    return {
      loading: false,
      modalTree: {
        treeData: {},
        selectedMenuData: {},
        selectedTreeKeys: [],
        changeMenuData: [], // 权限选择变化过的节点
        menuModalLoading: false, // 修改权限弹出框，确认按钮loading
        defaultExpandedKeys: [],
        loading: false,
        checkStrictly: false
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
                {this.$hasPerm('role:edit') ? (
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
                  {this.$hasPerm('role:edit') ? (
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
                  ) : (
                    ''
                  )}
                  {this.$hasPerm('role:edit') ? (
                    <a-button
                      type="primary"
                      size="small"
                      icon="solution"
                      onClick={() => {
                        this.tableSelected = row
                        this.permEditType = 'menu'
                        this.showMenuModal = true
                      }}
                    >
                    菜单
                    </a-button>
                  ) : (
                    ''
                  )}
                  {this.$hasPerm('role:edit') ? (
                    <a-button
                      type="primary"
                      size="small"
                      icon="solution"
                      onClick={() => {
                        this.tableSelected = row
                        this.permEditType = 'dept'
                        this.showMenuModal = true
                      }}
                    >
                    部门
                    </a-button>
                  ) : (
                    ''
                  )}
                  {this.$hasPerm('role:remove') ? (
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
      params: {},
      modalParams: {
        type: 'M'
      },
      showMenuModal: false,
      showRoleModal: false,
      tableSelected: {},
      editType: '',
      permEditType: ''
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
.tree-selected-node>.ant-tree-node-content-wrapper>.ant-tree-title{
  color: red
}
</style>
