import {getMgrMenus, getMgrMenuInfo, saveMgrMenuInfo } from '../../services/menus'
import { parse } from 'qs'
import { message } from 'antd'
export default {
  namespace: 'mgrMenus',
  state: {
    menus: [],
    menuInfo: {},
    menuPrivileges: [{"key":"1","roleId":"super.admin", "roleName": "系统管理员"}],
    formStatus: 'none',
    formEditable: false
  },
  subscriptions: {
    setup ({ dispatch }) {
      dispatch({ type: 'query' })
    },
  },
  effects: {
    *query ({ payload, }, {call, put}) {
        let menuData = yield call(getMgrMenus, parse(payload));
        if (menuData.success && menuData.result) {
          yield put({type: 'querySuccess',
            payload: menuData.result,
          })
        }
    },

    *showMenu ({payload}, {call, put}) {
      let menuInfo = yield call(getMgrMenuInfo, parse(payload));
      if (menuInfo.success && menuInfo.result) {
        yield put({type: 'showMenuInfo',
          payload: menuInfo.result,
        })
      }
    },

    *saveMenu ({payload}, {call, put}) {
         let succ = yield call(saveMgrMenuInfo, parse(payload));
         if (succ.success === true) {
           yield put({type: 'updateMenuInfo',
             payload: payload,
           })
           message.success("保存成功")
         }
    },

  },
  reducers: {
    querySuccess (state, {payload}){
        return {...state, menus: payload, menuInfo: {}}
    },
    showMenuInfo (state, {payload}){
      return {...state, menuInfo: payload }
    },

    showMenuPrivileges (state, {payload}){
      return {...state, menuPrivileges: payload }
    },

    toEditModel (state, {payload}){
      if (state.menuInfo.key) {
        return {...state, formEditable: true, formStatus: 'edit'}
      }else {
        message.info('请在左侧树上选择菜单')
      }
      return state
    },

    saveEditForm (state, {payload}){
      return {...state, formEditable: false, formStatus: 'save' }
    },

    cancelEditForm (state, {payload}){
      return {...state, formEditable: false, formStatus: 'cancel'  }
    },

    updateMenuInfo (state, {payload}) {
      let data = payload;
      if (data.fieldName === 'title'){
        let us = state.menuInfo
        let updateData = {userInfo: { ...us, title: data.value}}
        return {...state, ...updateData }
      }else if (data.fieldName === 'index'){
        let us = state.menuInfo
        let updateData = {userInfo: { ...us, index: data.value}}
        return {...state, ...updateData }
      }
      return {... state}
    },

  },
}
