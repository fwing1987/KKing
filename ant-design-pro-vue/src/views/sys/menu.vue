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
      <a-spin tip="加载中，请稍候..." :spinning="loading">
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
        />
      </a-spin>
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
            @select="parentTreeSelect"
            :disabled="modalParams.type == 'B' && editType == 'update'"
            v-decorator="['pMenu', { rules: [{ required: false }] }]"
          >
          </a-tree-select>
        </a-form-item>
        <a-form-item label="路径：" v-show="modalParams.type != 'B'" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-input
            type="text"
            v-decorator="['path', { rules: [{ required: modalParams.type != 'B', message: '请输入菜单路径' }] }]"
          >
          </a-input>
        </a-form-item>
        <a-form-item label="组件：" v-show="modalParams.type == 'M'" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-input
            type="text"
            v-decorator="['component', { rules: [{ required: modalParams.type == 'M', message: '请输入菜单组件' }] }]"
          >
          </a-input>
        </a-form-item>
        <a-form-item label="权限标识：" v-show="modalParams.type != 'D'" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-input-group compact>
            <a-input
              type="text"
              v-decorator="['permName']"
              placeholder="资源名"
              style="width:50%"
              :disabled="modalParams.type != 'M'"
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
        <a-form-item label="图标：" v-show="modalParams.type != 'B'" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-input type="text" v-decorator="['icon']"> </a-input>
        </a-form-item>
        <a-form-item label="排序：" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-input type="number" v-decorator="['sort']"> </a-input>
        </a-form-item>
        <a-form-item label="类型：" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-radio-group
            buttonStyle="solid"
            :disabled="editType == 'update'"
            v-model="modalParams.type"
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
    regetTreeData () {
      if (this.modalTreeData.length <= 0) {
        getMenuList({}).then(res => {
          this.modalTreeData = [{ id: 0, name: '主菜单', type: 'D' }]
          this.modalTreeData[0].children = _.cloneDeep(res.data.menus)
          this.makeTreeDataSafe(this.modalTreeData)
        })
      }
    },
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
    parentTreeSelect (value, node, extra) {
      this.parentTreeSelected = extra.selectedNodes[0].data.props
    },
    getMenuById (data, id) {
      if (id === null) return null
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
      this.modalParams = {
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
        this.$error({ title:  '按钮下不能创建子项' })
        return
      }
      this.editType = 'add'
      this.initMenuParams()
      this.modalParams.pMenu = this.tableSelected.name ? this.tableSelected.name : '主菜单'
      this.modalParams.pid = this.tableSelected.id ? this.tableSelected.id : 0
      if (this.tableSelected.type === 'M') {
        this.modalParams.type = 'B'
      }
      var parentMenu = this.getMenuById(this.modalTreeData, this.tableSelected.pid)
      if (this.tableSelected.type === 'B' && parentMenu.permName) {
        this.modalParams.permName = this.parentMenu.permName
      } else if(this.tableSelected.permName) {
        this.modalParams.permName = this.tableSelected.permName
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
      _.merge(this.modalParams, this.tableSelected)
      var parentMenu = this.getMenuById(this.modalTreeData, this.modalParams.pid)
      this.modalParams.pMenu = parentMenu ? parentMenu.name : '主菜单'
      if (this.modalParams.type === 'B') {
        // 按钮使用上级菜单资源名
        this.modalParams.permName = parentMenu.permName
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
              this.$error({ title: `删除失败：${res.msg}` })
            }
          })
        }
      })
    },
    makeMenuTypeSafe (rule, value, cb) {
      var parentType = 'D'
      if (this.parentTreeSelected.type) {
        // 用户自己选择父菜单
        parentType = this.parentTreeSelected.type
      } else if (this.modalParams.pid) {
        // 对话框刚打开时
        var parentMenu = this.getMenuById(this.modalTreeData, this.modalParams.pid)
        parentType = parentMenu ? parentMenu.type : 'D';
      }

      if (parentType === 'M' && value !== 'B') {
        cb(new Error('菜单下只能包含按钮'))
      } else if (parentType === 'D' && value === 'B') {
        cb(new Error('目录下不能包含按钮'))
      } else if (parentType === 'B') {
        cb(new Error('按钮下不能包含子项'))
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
                this.$error({ title: `添加失败：${res.msg}` })
              }
            })
          } else if (this.editType === 'update') {
            // 修改父菜单
            var isChangePid = false
            if (this.parentTreeSelected.id !== null) {
              values.pid = this.parentTreeSelected.id
              if (values.pid === values.id) {
                this.$error({ title: `父菜单不能是自身` })
                return
              }
              isChangePid = true
            }
            updateMenu(values).then(res => {
              if (!res.code) {
                this.showMenuModal = false
                this.$message.success('修改成功', 5)
                this.getData()
                if (isChangePid) {
                  // 更新下树结构
                  this.modalTreeData = []
                  this.regetTreeData()
                }
              } else {
                this.$error({ title: `修改失败：${res.msg}` })
              }
            })
          }
        }
      })
    },
    getData () {
      this.loading = true
      getMenuList(this.params).then(res => {
        this.loading = false
        this.data = res.data.menus
        this.actions = res.data.actions.map(item => item.action_name)
        // 设置第一层级默认展开
        this.expandedRowKeys.push(..._.map(this.data, 'id'))
        if (Object.keys(this.params).length == 0 && this.modalTreeData.length <= 0) {
          this.modalTreeData = [{ id: 0, name: '主菜单', type: 'D' }]
          this.modalTreeData[0].children = _.cloneDeep(this.data)
          this.makeTreeDataSafe(this.modalTreeData)
        }
      })
    },
    makeTreeDataSafe (data) {
      // 修改为antd控件需要的不同属性名
      for (var i = data.length - 1; i >= 0; i--) {
        var item = data[i]
        if (item.type === 'B') {
          data.splice(i, 1)
        }
        item.title = item.name
        item.key = item.id
        item.value = item.name
        if (item.children) {
          this.makeTreeDataSafe(item.children)
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
        this.parentTreeSelected = {}
        this.$nextTick(() => {
          this.form.setFieldsValue(this.modalParams)
        })
      }
    }
  },
  data () {
    return {
      loading: false,
      parentTreeSelected: {},
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
      params: {},
      modalParams: {
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
