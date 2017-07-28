import pathToRegexp from 'path-to-regexp'
import { getEmployee } from '../../services/employee'

export default {
  namespace: 'employeeView',
  state: {
    employee: {
      id: null,
      name: null,
      sex: null,
      telNumber: null,
      address: null,
    },
  },

  subscriptions: {
    setup ({ dispatch, history }) {
      history.listen((location) => {
        const match = pathToRegexp('/employee/employeeView/:id').exec(location.pathname)
        if (match) {
          dispatch({
            type: 'getEmployee',
            payload: { id: match[1] },
          })
        }
      })
    },
  },

  effects: {
    * getEmployee ({ payload }, { call, put }) {
      const data = yield call(getEmployee, payload)  // 调用远程服务获取结果
      if (data.success) { // 判断获取结果是否成功
        yield put({
          type: 'getEmployeeSuccess',
          payload: {
            employee: data.rows[0],
          },
        })  // 将返回结果赋值给state
      } else {
        let error = { message: data.message }
        throw (error) // 抛出错误信息，交给dva处理
      }
    },
  },

  reducers: {
    getEmployeeSuccess (state, action) {
      return {
        ...state,
        ...action.payload,
      }
    },
  },
}
