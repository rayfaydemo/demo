module.exports = {
  name: '应用运营平台',
  prefix: 'antdAdmin',
  footerText: '应用运营平台  © 2017 rayfay',
  logo: '/logo.png',
  iconFontCSS: '/iconfont.css',
  iconFontJS: '/iconfont.js',
  baseURL: 'http://demo-management.apps.cftest.net/',
  YQL: ['http://www.zuimeitianqi.com'],
  CORS: ['http://localhost:7000', 'http://localhost:8000', 'http://localhost:8088', 'http://localhost:8082', 'http://localhost:8080', 'http://demo-management.apps.cftest.net'],
  openPages: ['/login'],
  apiPrefix: '/api/v1',
  serviceDomain: '/hosts/demo-application',
  defaultPageSize: 20,
  api: {
    userLogin: '/user/login',
    userLogout: '/user/logout',
    userInfo: '/userInfo',
    users: '/users',
    user: '/p/user/:id',
    menus: '/p/menus',
    dashboard: '/dashboard',
    // custom
    mgr_menus: '/p/mgr/menus',
    info_menus: '/p/mgr/getMenuInfo/:menuId',
    save_menus: '/p/mgr/saveMenuInfo/:menuId',
    query_accounts: '/p/mgr/listUserAccounts',
    find_org_tree: '/p/mgr/findChildOrg',
    search_users: '/p/mgr/searchUsers?threshold=100',
    add_accounts: '/p/mgr/addUserAccounts',
    show_accounts: '/p/mgr/showUserAccountDetails/:id',
    remove_accounts: '/p/mgr/removeUserAccounts',
    enable_accounts: '/p/mgr/enabledUserAccounts?enabled=true',
    disable_accounts: '/p/mgr/enabledUserAccounts?enabled=false',
    query_usergroups: '/p/mgr/listUserGroups',
    add_usergroups: '/p/mgr/addUserGroups',
    remove_usergroups: '/p/mgr/removeUserGroups',
    retrieve_usergroups: '/p/mgr/getUserGroups/:groupId',
    query_groupaccounts: '/p/mgr/listGroupUsers/:groupId',
    add_groupaccounts: '/p/mgr/addGroupUsers/:groupId',
    remove_groupaccounts: '/p/mgr/removeGroupUsers/:groupId',
    list_groupTypes: '/p/mgr/listGroupTypes',
    update_groupRoles: '/p/mgr/updateGroupRoles/:groupId',
    retrieve_groupRoles: '/p/mgr/listUserGroupRoles/:groupId',

    search_Account: 'p/mgr/searchUserAccounts',
    role_type: '/p/mgr/listUserRoleTypes',
    query_role: '/p/mgr/listUserRoles',
    save_role: '/p/mgr/addUserRoles',
    update_role: '/p/mgr/updateUserRoles',
    get_role: '/p/mgr/getUserRoles//:roleId',
    del_role: '/p/mgr/removeUserRoles',
    get_rolePrivileges: '/p/mgr/getRolePrivileges//:roleId',
    save_rolePrivileges: '/p/mgr/updateRolePrivileges//:roleId',

  },
}
