import React from 'react'
import PropTypes from 'prop-types'
import { BackTop } from 'antd'
import { connect } from 'dva'
import NProgress from 'nprogress'
import { Helmet } from 'react-helmet'
import { Layout } from '../components'
import { classnames, config, menu, treeToArray, arrayToTree } from '../utils'
import '../themes/index.less'
import './app.less'


const { prefix } = config

const { Header, Bread, Footer, Sider, styles } = Layout

const App = ({ children, location, dispatch, app, loading }) => {
  const { menus, user, personal, siderFold, darkTheme, isNavbar, menuPopoverVisible, navOpenKeys } = app
  let customMenus = arrayToTree(menu, 'key', 'mpid');
  let userMenus = [...menus, ...customMenus]
  NProgress.start()
  !loading.global && NProgress.done()
  const headerProps = {
    menu: userMenus,
    user,
    siderFold,
    location,
    isNavbar,
    menuPopoverVisible,
    navOpenKeys,
    personal,
    switchMenuPopover () {
      dispatch({ type: 'app/switchMenuPopver' })
    },
    logout () {
      dispatch({ type: 'app/logout' })
    },
    switchSider () {
      dispatch({ type: 'app/switchSider' })
    },
    changeOpenKeys (openKeys) {
      dispatch({ type: 'app/handleNavOpenKeys', payload: { navOpenKeys: openKeys } })
    },
  }

  const siderProps = {
    menu: userMenus,
    siderFold,
    darkTheme,
    location,
    navOpenKeys,
    changeTheme () {
      dispatch({ type: 'app/switchTheme' })
    },
    changeOpenKeys (openKeys) {
      localStorage.setItem(`${prefix}navOpenKeys`, JSON.stringify(openKeys))
      dispatch({ type: 'app/handleNavOpenKeys', payload: { navOpenKeys: openKeys } })
    },
    // MenuItem 菜单点击事件
    handleClickNavMenu(item){
      let menuId = item.id || item.key;
      let activeMenu = {"key": menuId};
      console.log('Click ' + menuId);
      localStorage.setItem(`${prefix}navActiveMenu`, JSON.stringify(activeMenu))
      dispatch({ type: 'app/handleActiveMenu', payload: { activeMenu: activeMenu } })
    }
  }
  const menuAsArray = treeToArray(userMenus)
  const breadProps = {
    menu: menuAsArray,
  }

  if (config.openPages && config.openPages.indexOf(location.pathname) > -1) {
    return <div>{children}</div>
  }

  const { iconFontJS, iconFontCSS } = config

  return (
    <div>
      <Helmet>
        <title>应用运营平台</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="icon" href={config.logoSrc} type="image/x-icon" />
        {iconFontJS && <script src={iconFontJS}></script>}
        {iconFontCSS && <link rel="stylesheet" href={iconFontCSS} />}
      </Helmet>
      <div className={classnames(styles.layout, { [styles.fold]: isNavbar ? false : siderFold }, { [styles.withnavbar]: isNavbar })}>
        {!isNavbar ? <aside className={classnames(styles.sider, { [styles.light]: !darkTheme })}>
          <Sider {...siderProps} />
        </aside> : ''}
        <div className={styles.main} id="main">
          <BackTop target={() => document.getElementById('main')} />
          <Header {...headerProps} />
          <Bread {...breadProps} location={location} />
          <div className={styles.container}>
            <div className={styles.content}>
              {children}
            </div>
          </div>
          <Footer />
        </div>
      </div>
    </div>
  )
}

App.propTypes = {
  children: PropTypes.element.isRequired,
  location: PropTypes.object,
  dispatch: PropTypes.func,
  app: PropTypes.object,
  loading: PropTypes.object,
}

export default connect(({ app, loading }) => ({ app, loading }))(App)
