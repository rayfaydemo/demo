import { getMgrMenus } from '../../services/menus'
import { getRoleType, getRoleList, saveRole, getRole, deleteRole, getRolePrivileges, saveRolePrivileges } from '../../services/role'
import { message } from 'antd'
export default {
  namespace: 'roleList',
  state: {
    resultList: [],
    menus: [],
    roleTypeList: [],
    loading: false,
    visible: false,
    pagination: {
      showSizeChanger: true,
      showQuickJumper: true,
      showTotal: total => `共 ${total} 条`,
      current: 1,
      total: null,
    },
    searchData: {},
    formData: {
      roleId: '',
      roleName: '',
      roleType: '',
    },
    selectRole: '',
    editDisabled: false,
    selectTreeNode: [],
    selectRecord: '',
    display:{
      style:'none',
      tableWidth:24,
      margin:0,
      treeWidth:0,
    },
    treeEdit:false,
  },
  subscriptions: {
    setup ({ dispatch, history }) {
      history.listen(location => {
        dispatch({ type: 'queryMenu' })
        dispatch({ type: 'queryRoleType' })
        dispatch({
          type: 'queryRole',
          payload: location.query })
      })
    },

  },
  effects: {
    *queryMenu ({ payload }, { call, put }) {
      let menuData = yield call(getMgrMenus, payload)
      if (menuData.success && menuData.result) {
        yield put({ type: 'getMenuSuccess',
          payload: menuData.result,
        })
      }
    },

    *queryRoleType ({ payload }, { call, put }) {
      let typeData = yield call(getRoleType, payload)
      if (typeData.success && typeData.result) {
        yield put({ type: 'getTypeSuccess',
          payload: typeData.result.rows,
        })
      }
    },

    *queryRole ({ payload }, { call, put }) {
      yield put({ type: 'showLoading' })
      const data = yield call(getRoleList, payload)
      if (data) {
        yield put({
          type: 'querySuccess',
          payload: {
            resultList: data.result.rows,
            pagination: {
              current: Number(payload.currentPage),
              pageSize: Number(payload.pageSize),
              total: data.result.totalNum,
            },
            searchData: {
              queryData: payload.queryData,
              sorter: payload.sorter,
            },
            loading: false,
          },
        })
      } else {
        yield put({ type: 'HideLoading' })
        message.error('获取数据失败！')
      }
    },

    *delRole ({ payload }, { call, put }) {
      const data = yield call(deleteRole, payload.roleId)
      if (data) {
        if (data.success) {
          message.success('删除成功！')
          const listData = yield call(getRoleList, payload)
          if (listData) {
            yield put({
              type: 'querySuccess',
              payload: {
                resultList: listData.result.rows,
                pagination: {
                  current: Number(payload.currentPage),
                  pageSize: Number(payload.pageSize),
                  total: listData.result.totalNum,
                },
                loading: false,
              },
            })
          } else {
            yield put({ type: 'HideLoading' })
            message.error('获取数据失败！')
          }
        } else {
          message.error(data.message)
        }
      } else {
        message.error('删除数据失败！')
      }
    },


    *saveRole ({ payload }, { call, put }) {
      const data = yield call(saveRole, payload)
      if (data) {
        if (data.success) {
          payload.resetFields()
          yield put({
            type: 'closeModal',
            payload: {
              resetFields: payload.resetFields,
            },
          })
          message.success('保存成功！')
          const listData = yield call(getRoleList, payload)
          if (listData) {
            yield put({
              type: 'querySuccess',
              payload: {
                resultList: listData.result.rows,
                pagination: {
                  current: Number(payload.currentPage),
                  pageSize: Number(payload.pageSize),
                  total: listData.result.totalNum,
                },
                loading: false,
              },
            })
          } else {
            yield put({ type: 'HideLoading' })
            message.error('获取数据失败！')
          }
        } else {
          message.error(data.message)
        }
      } else {
        message.error('保存数据失败！')
      }
    },

    *showModal ({ payload }, { call, put }) {
      if (payload.modalType == 'create') {
        yield put({
          type: 'showSuccess',
          payload: {
            formData: {
              roleId: '',
              roleName: '',
              roleType: '',
            },
            editDisabled: false,
          },
        })
      } else {
        const data = yield call(getRole, payload.roleId)
        yield put({
          type: 'showSuccess',
          payload: {
            formData: data.result,
            editDisabled: true,
          },
        })
      }
    },


    *getRolePrivileges ({ payload }, { call, put }) {
      let data = yield call(getRolePrivileges, payload.record.roleId)
      if (data.success && data.result) {
        console.log(data.result)
        yield put({ type: 'getRolePrivilegesSuccess',
          payload: {
            selectTreeNode: data.result,
            selectRecord: payload.record,
            display:{
              style:'',
              tableWidth:16,
              margin:1,
              treeWidth:7,
            },
            treeEdit:false,
          },
        })
      }
    },

    *setChecks ({ payload }, { call, put }) {
      yield put({ type: 'setChecksSuccess',
        payload: {
          selectTreeNode: payload.checks,
          treeEdit: true,
        },
      })
    },


    *saveRolePrivileges ({ payload }, { call, put }) {
      let data = yield call(saveRolePrivileges, payload)
      if (data) {
        if (data.success) {
          message.success('保存成功！')
          yield put({ type: 'setTreeEdit',
            payload: {
              treeEdit: false,
            },
          })
        } else {
          message.error(data.message)
          throw (message)
        }
      } else {
        message.error('保存数据失败!')
      }
    },


  },
  reducers: {
    getMenuSuccess (state, { payload }) {
      return { ...state, menus: payload, menuInfo: {}, selectTreeNode: [], selectRecord: {} }
    },
    getTypeSuccess (state, { payload }) {
      let list = []
      payload.forEach((ele) => {
        list.push({ text: ele.typeName, value: ele.typeId })
      })
      return { ...state, roleTypeList: list }
    },
    querySuccess (state, action) {
      const { resultList, pagination, searchData } = action.payload
      return {
        ...state,
        resultList,
        pagination: {
          ...state.pagination,
          ...pagination,
        },
        searchData: {
          ...state.searchData,
          ...searchData,
        },
        loading: false,
      }
    },

    showSuccess (state, action) {
      const { formData, editDisabled } = action.payload
      return {
        ...state,
        formData,
        editDisabled,
        visible: true }
    },

    closeModal (state, action) {
      const { resetFields } = action.payload
      resetFields()
      return {
        ...state,
        visible: false }
    },

    setSelect (state, action) {
      const { selectRow } = action.payload
      return {
        ...state,
        selectRole: selectRow }
    },

    getRolePrivilegesSuccess (state, action) {
      const { selectTreeNode, selectRecord,display,treeEdit } = action.payload
      return {
        ...state,
        selectTreeNode,
        selectRecord,
        display,
        treeEdit,
      }
    },

    setChecksSuccess (state, action) {
      const { selectTreeNode,treeEdit } = action.payload
      return {
        ...state,
        selectTreeNode,
        treeEdit}
    },

    setTreeEdit (state, action) {
      const { treeEdit } = action.payload
      return {
        ...state,
        treeEdit}
    },

  },
}
