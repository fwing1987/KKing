<template>
  <div>
    <a-card style="margin-bottom:10px" v-action:list>
      <a-form layout="inline">
        <a-row :gutter="16" type="flex" justify="start">
          <a-col :span="6">
            <a-form-item label="名称：">
              <a-input type="text" v-model="params.name"> </a-input>
            </a-form-item>
          </a-col>
          <a-col :span="6">
            <a-form-item label="类型：">
              <a-radio-group v-model="params.state">
                <a-radio-button value="0">显示</a-radio-button>
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
          @click="addMenuClick"
          v-action:add
        >
          <a-icon type="plus" />新增
        </a-button>
        <a-button type="primary" @click="editMenuClick" v-action:edit> <a-icon type="edit" />修改 </a-button>
        <a-button type="danger" @click="deleteMenuClick" v-action:remove> <a-icon type="delete" />删除 </a-button>
      </a-button-group>
      <a-table
        style="margin-top:10px"
        :columns="header"
        :dataSource="data"
        rowKey="id"
        :defaultExpandedRowKeys="expandedRowKeys"
        :rowSelection="rowSelection"
        :customRow="customRow"
        :pagination="false"
      ></a-table>
    </a-card>

    <a-modal v-model="showMenuModal" title="菜单" :maskClosable="false" @ok="editMenu">
      <a-form :form="form">
        <a-form-item v-show="false">
          <a-input type="hidden" v-decorator="['id']"></a-input>
        </a-form-item>
        <a-form-item v-show="false">
          <a-input type="hidden" v-decorator="['pid']"></a-input>
        </a-form-item>

        <a-form-item label="名称：" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-input type="text" v-decorator="['name', { rules: [{ required: true, message: '请输入菜单名' }] }]">
          </a-input>
        </a-form-item>
        <a-form-item label="父菜单：" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-tree-select
            style="width:100%"
            :dropdownStyle="{ maxHeight: '400px', overflow: 'auto' }"
            :treeData="modalTreeData"
            :treeDefaultExpandedKeys="expandedRowKeys"
            treeNodeLabelProp="name"
            treeNodeFilterProp="id"
            @select="parentMenuTreeSelect"
            v-decorator="['pMenu', { rules: [{ required: false }] }]"
          >
          </a-tree-select>
        </a-form-item>
        <a-form-item label="路径：" v-show="menuParams.type != 'B'" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-input
            type="text"
            v-decorator="['path', { rules: [{ required: menuParams.type != 'B', message: '请输入菜单路径' }] }]"
          >
          </a-input>
        </a-form-item>
        <a-form-item label="组件：" v-show="menuParams.type == 'M'" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-input
            type="text"
            v-decorator="['component', { rules: [{ required: menuParams.type == 'M', message: '请输入菜单组件' }] }]"
          >
          </a-input>
        </a-form-item>
        <a-form-item label="权限标识：" v-show="menuParams.type != 'D'" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-input-group compact>
            <a-input
              type="text"
              v-decorator="['permName']"
              placeholder="资源名"
              style="width:50%"
              :disabled="menuParams.type != 'M'"
            >
            </a-input>
            <a-auto-complete
              style="width:50%"
              v-decorator="['actionName']"
              :dataSource="actions"
              placeholder="操作名"
            />
          </a-input-group>
        </a-form-item>
        <a-form-item label="图标：" v-show="menuParams.type != 'B'" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-input type="text" v-decorator="['icon']"> </a-input>
        </a-form-item>
        <a-form-item label="排序：" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-input type="number" v-decorator="['sort']"> </a-input>
        </a-form-item>
        <a-form-item label="类型：" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-radio-group
            buttonStyle="solid"
            :disabled="editType == 'update'"
            v-model="menuParams.type"
            v-decorator="[
              'type',
              { rules: [{ required: true, message: '请选择菜单类型' }, { validator: makeMenuTypeSafe }] }
            ]"
          >
            <a-radio-button value="D">目录</a-radio-button>
            <a-radio-button value="M">菜单</a-radio-button>
            <a-radio-button value="B">按钮</a-radio-button>
          </a-radio-group>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>
<script>
import { getMenuList, addMenu, updateMenu, deleteMenu } from '@/api/menu'
export default {
  methods: {
    customRow (record) {
      return {
        on: {
          click: () => {
            this.rowSelection.selectedRowKeys = [record.id]
            this.tableSelected = record
          }
        }
      }
    },
    parentMenuTreeSelect (value, node, extra) {
      this.parentMenuTreeSelected = extra.selectedNodes[0].data.props
    },
    getMenuById (data, id) {
      if (!id) return null
      for (var i = 0; i < data.length; i++) {
        if (data[i].id === id) {
          return data[i]
        }
        if (data[i].children) {
          var ret = null
          if ((ret = this.getMenuById(data[i].children, id))) {
            return ret
          }
        }
      }
    },
    initMenuParams () {
      this.menuParams = {
        pMenu: '',
        pid: '',
        type: 'M',
        permName: '',
        name: '',
        path: '',
        component: '',
        actionName: '',
        icon: ''
      }
    },
    addMenuClick () {
      if (this.tableSelected.type === 'B') {
        this.$error('按钮下不能创建子项', 5)
        return
      }
      this.editType = 'add'
      this.initMenuParams()
      this.menuParams.pMenu = this.tableSelected.name ? this.tableSelected.name : '主菜单'
      this.menuParams.pid = this.tableSelected.id ? this.tableSelected.id : 0
      if (this.tableSelected.type === 'M') {
        this.menuParams.type = 'B'
      }
      var parentMenu = this.getMenuById(this.modalTreeData, this.tableSelected.pid)
      if (this.tableSelected.type === 'B' && this.parentMenu.permName) {
        this.menuParams.permName = this.parentMenu.permName
      } else if(this.tableSelected.permName) {
        this.menuParams.permName = this.tableSelected.permName
      }
      
      this.showMenuModal = true
    },
    editMenuClick () {
      if (!this.tableSelected.id) {
        this.$message.warning('请选择要修改的菜单项', 5)
        return
      }

      this.editType = 'update'
      this.initMenuParams()
      _.merge(this.menuParams, this.tableSelected)
      var parentMenu = this.getMenuById(this.modalTreeData, this.menuParams.pid)
      this.menuParams.pMenu = parentMenu.name
      if (this.menuParams.type === 'B') {
        // 按钮使用上级菜单资源名
        this.menuParams.permName = parentMenu.permName
      }

      this.showMenuModal = true
    },
    deleteMenuClick () {
      if (!this.tableSelected.id) {
        this.$message.warning('请选择要删除的菜单项', 5)
        return
      }
      this.$confirm({
        title: `确认删除菜单【${this.tableSelected.name}】吗？`,
        onOk: () => {
          deleteMenu(this.tableSelected.id).then(res => {
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
    makeMenuTypeSafe (rule, value, cb) {
      var parentType = 'D'
      if (this.parentMenuTreeSelected.type) {
        // 用户自己选择父菜单
        parentType = this.parentMenuTreeSelected.type
      } else if (this.menuParams.pid) {
        // 对话框刚打开时
        var parentMenu = this.getMenuById(this.modalTreeData, this.menuParams.pid)
        parentType = parentMenu.type
      }

      if (parentType === 'M' && value !== 'B') {
        cb(new Error('菜单下只能创建按钮'))
      } else if (parentType === 'D' && value === 'B') {
        cb(new Error('目录下不能创建按钮'))
      } else if (parentType === 'B') {
        cb(new Error('按钮下不能创建子项'))
      }
      cb()
    },
    editMenu () {
      this.form.validateFields((err, values) => {
        if (!err) {
          if (this.editType === 'add') {
            addMenu(values).then(res => {
              if (!res.code) {
                this.showMenuModal = false
                this.$message.success('添加成功', 5)
                this.getData()
              } else {
                this.$error(`添加失败：${res.msg}`, 5)
              }
            })
          } else if (this.editType === 'update') {
            updateMenu(values).then(res => {
              if (!res.code) {
                this.showMenuModal = false
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
    getData () {
      getMenuList(this.params).then(res => {
        this.data = res.data.menus
        this.actions = res.data.actions.map(item => item.action_name)
        // 设置第一层级默认展开
        this.expandedRowKeys.push(..._.map(this.data, 'id'))
        this.modalTreeData = [{ id: 0, name: '主菜单', type: 'D' }]
        this.modalTreeData[0].children = _.cloneDeep(this.data)
        this.makeTreeDataSafe(this.modalTreeData)
      })
    },
    makeTreeDataSafe (data) {
      // 修改为antd控件需要的不同属性名
      for (var i = 0; i < data.length; i++) {
        data[i].title = data[i].name
        data[i].key = data[i].id
        data[i].value = data[i].name
        if (data[i].children) {
          this.makeTreeDataSafe(data[i].children)
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
        this.parentMenuTreeSelected = {}
        this.$nextTick(() => {
          this.form.setFieldsValue(this.menuParams)
        })
      }
    }
  },
  data () {
    return {
      parentMenuTreeSelected: {},
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
          title: '名称',
          dataIndex: 'name',
          customRender: (value, row, index) => {
            var ret = []
            // 添加icon
            if (row.icon) {
              ret.push(<a-icon type={row.icon} style="font-size:14px;padding-right:3px" />)
            } else {
              ret.push(<span style="padding-right:17px" />)
            }
            return (
              <span>
                {ret}
                <span>{value}</span>
              </span>
            )
          }
        },
        {
          title: '路径',
          dataIndex: 'path'
        },
        {
          title: '组件',
          dataIndex: 'component'
        },
        {
          title: '权限标识',
          dataIndex: 'permName',
          customRender: (value, row, index) => {
            return <span>{row.permName ? row.permName + ':' + row.actionName : ''}</span>
          }
        },
        {
          title: '类型',
          dataIndex: 'type',
          customRender: (type, row, index) => {
            return (
              <div>
                {type === 'D' ? (
                  <a-tag color="blue">目录</a-tag>
                ) : type === 'M' ? (
                  <a-tag color="green">菜单</a-tag>
                ) : (
                  <a-tag color="volcano">按钮</a-tag>
                )}
              </div>
            )
          }
        },
        {
          title: '状态',
          dataIndex: 'state',
          customRender: (state, row, index) => {
            return <div>{state === 0 ? <a-tag color="cyan">显示</a-tag> : <a-tag color="red">隐藏</a-tag>}</div>
          }
        },
        {
          title: '排序',
          dataIndex: 'sort'
        },
        {
          title: '操作',
          customRender: (state, row, index) => {
            return (
              <div>
                <a-button-group>
                  {this.$hasAction('add') ? (
                    <a-tooltip title="新增">
                      <a-button
                        style={{ visibility: row.type === 'B' ? 'hidden' : '' }}
                        type="primary"
                        size="small"
                        icon="plus"
                        onClick={() => {
                          this.tableSelected = row
                          this.addMenuClick()
                        }}
                      />
                    </a-tooltip>
                  ) : (
                    ''
                  )}
                  {this.$hasAction('edit') ? (
                    <a-tooltip title="修改">
                      <a-button
                        type="primary"
                        size="small"
                        icon="edit"
                        onClick={() => {
                          this.tableSelected = row
                          this.editMenuClick()
                        }}
                      />
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
                          this.deleteMenuClick()
                        }}
                      />
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
      modalTreeData: [],
      actions: [],
      params: {
        name: '',
        type: ''
      },
      menuParams: {
        type: 'M'
      },
      showMenuModal: false,
      tableSelected: {},
      editType: '',
      expandedRowKeys: []
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
</style>
