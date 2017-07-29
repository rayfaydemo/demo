import { config } from '../../utils'
import { getEmployeeTableDataSource, createEmployee, updateEmployee, deleteEmployee } from '../../services/employee'

const { defaultPageSize } = config

export default {
  namespace: 'employeeList',
  state: {
    employeeTableDataSource: [],  // 用来保存列表数据
    employeeTableLoading: false,  // 控制表格加载效果
    txtEmployeeName: null,  // 用来保存文本框输入的值
    pagination: {
      showSizeChanger: true,  // 是否可以改变 pageSize
      showQuickJumper: true,  // 是否可以快速跳转至某页
      showTotal: total => `共 ${total} 条`, // 用于显示数据总量和当前数据顺序
      current: 1, // 当前页数
      total: 0,  // 数据总数
    },  // 用来保存翻页控件的信息
    createModelVisible: false,
    editObj: null,
  },

  subscriptions: {
    setup ({ dispatch, history }) {
      history.listen((location) => {
        if (location.pathname === '/employee/employeeList') {
          dispatch({
            type: 'getEmployeeTableDataSource',
            payload: {},
          })
        }
      })
    },
  },

  effects: {
    * getEmployeeTableDataSource ({ payload }, { call, put }) {
      yield put({ type: 'showLoading' })  // 显示加载遮罩
      const data = yield call(getEmployeeTableDataSource, payload)  // 调用远程服务获取结果
      if (data.success) { // 判断获取结果是否成功
        yield put({
          type: 'getEmployeeTableDataSourceSuccess',
          payload: {
            employeeTableDataSource: data.rows,
            pagination: {
              current: typeof (payload.currentPage) === 'undefined' ? 1 : Number(payload.currentPage),
              pageSize: typeof (payload.pageSize) === 'undefined' ? defaultPageSize : Number(payload.pageSize),
              total: data.rowsTotal,
            },
            employeeTableLoading: false,
          },
        })  // 将返回结果赋值给state
      } else {
        yield put({ type: 'HideLoading' })
        let error = { message: data.message }
        throw (error) // 抛出错误信息，交给dva处理
      }
    },

    * saveEmployee ({ payload }, { call, put }) {
      const { employee, isCreate } = payload
      let data
      if (isCreate) {
        data = yield call(createEmployee, employee)  // 调用远程服务获取结果
      } else {
        data = yield call(updateEmployee, employee)
      }

      if (data.success) { // 判断获取结果是否成功
        yield put({ type: 'setCreateModelVisible', payload: { createModelVisible: false, editObj: null } })
        yield put({ type: 'getEmployeeTableDataSource', payload: {} })
      } else {
        yield put({ type: 'HideLoading' })
        let error = { message: data.message }
        throw (error) // 抛出错误信息，交给dva处理
      }
    },

    * deleteEmployee ({ payload }, { call, put }) {
      let data = yield call(deleteEmployee, payload)
      if (data.success) { // 判断获取结果是否成功
        yield put({ type: 'setCreateModelVisible', payload: { createModelVisible: false, editObj: null } })
        yield put({ type: 'getEmployeeTableDataSource', payload: {} })
      } else {
        yield put({ type: 'HideLoading' })
        let error = { message: data.message }
        throw (error) // 抛出错误信息，交给dva处理
      }
    },
  },

  reducers: {
    getEmployeeTableDataSourceSuccess (state, action) {
      const { employeeTableDataSource, pagination, employeeTableLoading } = action.payload // 从payload中展开需要的属性
      return {
        ...state,
        employeeTableDataSource,
        employeeTableLoading,
        pagination: {
          ...state.pagination,
          ...pagination,
        },
      } // 展开原有state，并赋值新的属性值
    },

    showLoading (state) {
      return {
        ...state,
        employeeTableLoading: true, // 显示表格加载状态
      }
    },

    HideLoading (state) {
      return {
        ...state,
        employeeTableLoading: false,  // 关闭表格加载状态
      }
    },

    onChangeEmployeeName (state, action) {
      return {
        ...state,
        ...action.payload,
      }
    },

    setCreateModelVisible (state, action) {
      return {
        ...state,
        ...action.payload,
      }
    },

    editEmployee (state, action) {
      return {
        ...state,
        ...action.payload,
        createModelVisible: true,
      }
    },
  },
}
