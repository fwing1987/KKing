<template>
  <div>
    <a-card style="margin-bottom:10px">
      <a-form layout="inline">
        <a-row :gutter="16" type="flex" justify="start">
          <a-col :span="6">
            <a-form-item label="名称：">
              <a-input type="text" v-model="params.name"> </a-input>
            </a-form-item>
          </a-col>
          <a-col :span="7">
            <a-form-item label="负责人：">
              <a-input type="text" v-model="params.leader"> </a-input>
            </a-form-item>
          </a-col>
          <a-col :span="5">
            <a-form-item label="类型：">
              <a-radio-group v-model="params.state">
                <a-radio-button value="0">正常</a-radio-button>
                <a-radio-button value="1">禁用</a-radio-button>
              </a-radio-group>
            </a-form-item>
          </a-col>
          <a-col :span="2">
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
            @click="addClick"
            v-action:add
          >
            <a-icon type="plus" />新增
          </a-button>
          <a-button type="primary" @click="editClick" v-action:edit> <a-icon type="edit" />修改 </a-button>
          <a-button type="danger" @click="deleteClick" v-action:remove> <a-icon type="delete" />删除 </a-button>
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

    <a-modal v-model="showDeptModal" title="部门" :maskClosable="false" @ok="editDept">
      <a-form :form="form">
        <a-form-item v-show="false">
          <a-input type="hidden" v-decorator="['id']"></a-input>
        </a-form-item>
        <a-form-item>
          <a-input type="hidden" v-decorator="['pid']"></a-input>
        </a-form-item>
        <a-form-item label="名称：" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-input type="text" v-decorator="['name', { rules: [{ required: true, message: '请输入部门名' }] }]">
          </a-input>
        </a-form-item>
        <a-form-item label="父部门：" :label-col="labelCol" :wrapper-col="wrapperCol" >
          <a-tree-select
            style="width:100%"
            :dropdownStyle="{ maxHeight: '400px', overflow: 'auto' }"
            :treeData="modalTreeData"
            :treeDefaultExpandedKeys="expandedRowKeys"
            @select="parentTreeSelect"
            treeNodeLabelProp="name"
            treeNodeFilterProp="id"
            :disabled="modalParams.pid===0"
            v-decorator="['parrentName', { rules: [{ required: false, message: '请选择父部门' }] }]"
          />
        </a-form-item>
        <a-form-item label="负责人：" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-input type="text" v-decorator="['leader']"/>
        </a-form-item>
        <a-form-item label="联系电话：" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-input type="text" v-decorator="['phone']"/>
        </a-form-item>
        <a-form-item label="邮箱：" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-input type="text" v-decorator="['mail']"/>
        </a-form-item>
        <a-form-item label="状态：" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-switch
            v-decorator="['state', { valuePropName: 'checked' }]"
          />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>
<script>
import { getDeptList, addDept, updateDept, deleteDept } from '@/api/dept'

export default {
  methods: {
    regetTreeData () {
      if (this.modalTreeData.length <= 0) {
        getDeptList({}).then(res => {
          this.modalTreeData = _.cloneDeep(res.data)
          this.makeTreeDataSafe(this.modalTreeData)
        })
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
    parentTreeSelect (value, node, extra) {
      // 部门树父节点点击选中，记录父节点
      this.parentTreeSelected = extra.selectedNodes[0].data.props
    },
    initDeptParams () {
      this.modalParams = {
        state: 1,
        leader: '',
        phone: '',
        mail: '',
        name: ''
      }
    },
    getItemById (data, id) {
      if (!id) return null
      for (var i = 0; i < data.length; i++) {
        if (data[i].id === id) {
          return data[i]
        }
        if (data[i].children) {
          var ret = null
          if ((ret = this.getItemById(data[i].children, id))) {
            return ret
          }
        }
      }
    },
    addClick () {
      this.editType = 'add'
      this.initDeptParams()
      if (this.tableSelected.name) {
        this.modalParams.parrentName = this.tableSelected.name
        this.modalParams.pid = this.tableSelected.id
      } else if (this.modalTreeData[0].name) {
        this.modalParams.parrentName = this.modalTreeData[0].name
        this.modalParams.pid = this.modalTreeData[0].id
      }
      this.showDeptModal = true
    },
    editClick () {
      if (!this.tableSelected.id) {
        this.$message.warning('请选择要修改的部门项', 5)
        return
      }
      this.editType = 'update'
      this.initDeptParams()
      _.merge(this.modalParams, this.tableSelected)
      var parent = this.getItemById(this.modalTreeData, this.modalParams.pid)
      if (parent) {
        this.modalParams.parrentName = parent.name
      }
      this.modalParams.state = !this.modalParams.state
      this.showDeptModal = true
    },
    deleteClick () {
      if (!this.tableSelected.id) {
        this.$message.warning('请选择要删除的部门项', 5)
        return
      }
      this.$confirm({
        title: `确认删除部门【${this.tableSelected.name}】吗？`,
        onOk: () => {
          deleteDept(this.tableSelected.id).then(res => {
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
    editDept () {
      this.form.validateFields((err, values) => {
        if (!err) {
          values.state = !values.state
          if (this.editType === 'add') {
            addDept(values).then(res => {
              if (!res.code) {
                this.showDeptModal = false
                this.$message.success('添加成功', 5)
                this.getData()
              } else {
                this.$error({ title: `添加失败：${res.msg}` })
              }
            })
          } else if (this.editType === 'update') {
            // 修改父节点
            var isChangePid = false
            if (this.parentTreeSelected.id !== null) {
              values.pid = this.parentTreeSelected.id
              if (values.pid === values.id) {
                this.$error({ title: `父部门不能是自身` })
                return
              }
              isChangePid = true
            }
            updateDept(values).then(res => {
              if (!res.code) {
                this.showDeptModal = false
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
          this.modalTreeData = []
        }
      })
    },
    getData () {
      this.loading = true
      getDeptList(this.params).then(res => {
        this.loading = false
        this.data = res.data
        // 设置第一层级默认展开
        this.expandedRowKeys.push(..._.map(this.data, 'id'))
        this.makeTreeDataSafe(this.data)
        if (Object.keys(this.params).length === 0 && this.modalTreeData.length <= 0) {
          this.modalTreeData = _.cloneDeep(this.data)
        }
      })
    },
    makeTreeDataSafe (data) {
      // 修改为antd控件需要的不同属性名
      for (var i = 0; i < data.length; i++) {
        var item = data[i]
        item.title = item.name
        item.key = item.id
        item.value = item.name
        item.checked = item.state === 0// 正常状态state==0转换为开关状态1
        if (item.icon) {
          item.icon = (<a-icon type={item.icon} />)
        }
        if (item.children) {
          this.makeTreeDataSafe(item.children)
        }
      }
    },
    deptStateChange (checked, row) {
      // 修改部门状态
      row.state = !checked
      var msg = checked ? '启用' : '停用'
      this.$confirm({
        title: `确认要${msg}部门【${row.name}】吗？`,
        onOk: () => {
          updateDept(row).then(res => {
            if (!res.code) {
              this.$message.success('修改成功', 5)
              this.getData()
            } else {
              this.$error({ title: `修改失败：${res.msg}` })
            }
          })
        }
      })
    }
  },
  created () {
    this.getData()
  },
  watch: {
    showDeptModal (newVal) {
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
      menuTree: {
        menuData: {},
        selectedMenuData: {},
        selectedTreeKeys: [],
        changeMenuData: [], // 权限选择变化过的节点
        menuModalLoading: false // 修改权限弹出框，确认按钮loading
      },
      expandedRowKeys: [],
      parentTreeSelected: [],
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
          dataIndex: 'name'
        },
        {
          title: '负责人',
          dataIndex: 'leader'
        },
        {
          title: '手机',
          dataIndex: 'phone'
        },
        {
          title: '邮箱',
          dataIndex: 'mail'
        },
        {
          title: '状态',
          dataIndex: 'state',
          customRender: (state, row, index) => {
            return (
              <div>
                {this.$hasAction('edit') ? (
                  <a-switch checked={row.checked} onChange={(checked) => { this.deptStateChange(checked, row) }}/>
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
                  {this.$hasAction('add') ? (
                    <a-tooltip title="新增">
                      <a-button
                        style={{ visibility: row.type === 'B' ? 'hidden' : '' }}
                        type="primary"
                        size="small"
                        icon="plus"
                        onClick={() => {
                          this.tableSelected = row
                          this.addClick()
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
                          this.editClick()
                        }}
                      />
                    </a-tooltip>
                  ) : (
                    ''
                  )}
                  {this.$hasAction('remove') ? (
                    <a-tooltip title="修改">
                      <a-button
                        type="danger"
                        size="small"
                        icon="delete"
                        onClick={() => {
                          this.tableSelected = row
                          this.deleteClick()
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
      params: {},
      modalParams: {
        type: 'M'
      },
      showMenuModal: false,
      showDeptModal: false,
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
