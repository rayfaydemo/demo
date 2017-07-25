import { query, logout, getMenus } from '../services/app'
import { routerRedux } from 'dva/router'
import { parse } from 'qs'
import { config } from '../utils'
const { prefix } = config
export default {
  namespace: 'app',
  state: {
    menus: [],
    user: {},
    personal: { link: '/personal2', title: '个人信息' },
    menuPopoverVisible: false,
    siderFold: localStorage.getItem(`${prefix}siderFold`) === 'true',
    darkTheme: localStorage.getItem(`${prefix}darkTheme`) === 'true',
    isNavbar: document.body.clientWidth < 769,
    navOpenKeys: JSON.parse(localStorage.getItem(`${prefix}navOpenKeys`)) || [],
    // 当前激活的菜单ID
    activeMenu: { key: '' },
  },
  subscriptions: {

    setup ({ dispatch }) {
      dispatch({ type: 'query' })
      let tid
      window.onresize = () => {
        clearTimeout(tid)
        tid = setTimeout(() => {
          dispatch({ type: 'changeNavbar' })
        }, 300)
      }
    },

  },
  effects: {
    * query ({
      payload,
    }, { call, put }) {
      const data = yield call(query, parse(payload))
      if (data.success && data.user) {
        yield put({
          type: 'querySuccess',
          payload: data.user,
        })
        // 获取菜单信息
        const dataMenus = yield call(getMenus, parse(payload))
        if (dataMenus.success && dataMenus.result) {
          console.log('data menus got')
          yield put({
            type: 'queryMenus',
            payload: dataMenus.result,
          })
        }

        // 页面跳转
        if (location.pathname === '/login') {
          yield put(routerRedux.push('/dashboard'))
        }
      } else if (location.pathname !== '/login') {
        let from = location.pathname
        if (location.pathname === '/dashboard') {
          from = '/dashboard'
        }
        window.location = `${location.origin}/login?from=${from}`
      }
    },

    * logout ({ payload }, { call, put }) {
      let from = location.pathname
      if (location.pathname === '/dashboard') {
        from = '/dashboard'
      }
      window.location = `${location.origin}/logout?from=${from}`

      // const data = yield call(logout, parse(payload))
      // if (data.success) {
      //   yield put({ type: 'query' })
      // } else {
      //   throw (data)
      // }
    },
    * changeNavbar ({
      payload,
    }, { put, select }) {
      const { app } = yield (select(_ => _))
      const isNavbar = document.body.clientWidth < 769
      if (isNavbar !== app.isNavbar) {
        yield put({ type: 'handleNavbar', payload: isNavbar })
      }
    },

  },
  reducers: {
    querySuccess (state, { payload: user }) {
      return {
        ...state,
        user,
      }
    },

    queryMenus (state, { payload: menus }) {
      console.log('queryMenus success')
      // 处理Menus数据，进行Key值转换

      return {
        ...state,
        menus,
      }
    },

    switchSider (state) {
      localStorage.setItem(`${prefix}siderFold`, !state.siderFold)
      return {
        ...state,
        siderFold: !state.siderFold,
      }
    },

    switchTheme (state) {
      localStorage.setItem(`${prefix}darkTheme`, !state.darkTheme)
      return {
        ...state,
        darkTheme: !state.darkTheme,
      }
    },

    switchMenuPopver (state) {
      return {
        ...state,
        menuPopoverVisible: !state.menuPopoverVisible,
      }
    },

    handleNavbar (state, { payload }) {
      return {
        ...state,
        isNavbar: payload,
      }
    },

    handleNavOpenKeys (state, { payload: navOpenKeys }) {
      return {
        ...state,
        ...navOpenKeys,
      }
    },

    handleActiveMenu (state, { payload: activeMenu }) {
      return {
        ...state,
        activeMenu,
      }
    },
  },
}
