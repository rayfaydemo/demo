/**
 * Created by STZHANG on 2017/6/21.
 */
import {queryUserGroups, getRoleList, addUserGroups, removeUserGroups, retrieveUserGroups , queryGroupAccounts
 ,searchUserAccounts, loadGroupTypes, addUserRows, deleteUserRows, retrieveUserGroupPrivileges, updateUserGroupPrivileges } from '../../services/usergroups'
import {findOrgTree, showDetails} from '../../services/useraccount'
export default {
  namespace: 'userGroup',
  state: {
    userGroupList:[],
    accountList: [],
    searchedUsers:[],
    userRoles:[],
    groupDetail:{},
    groupPrivilegeDetail:{},
    selectedGroup: {},
    accountDetail:{},
    initGroupTypes:[],
    initTreeData:[],
    selectedRowKeys: [],
    groupModalStatus:{editDisabled:false,},

    //選擇的賬戶Keys
    selectedAccountKeys: [],

    queryDataWithGroup: {},
    queryDataWithAccount: {},
    showRight: false,
    modalVisible: false,
    modalPrivilegeVisible: false,

    modalAccountVisible: false,

    //usergroup edit model.
    loading: false,
    loadingRight: false,
    defaultSorterWithGroup:{"sortField": "groupCode","sortDirection": "ASC"},
    defaultSorterWithAccount:{"sortField": "userId","sortDirection": "ASC"},
    paginationWithGroup: {
      showSizeChanger: true,
      showQuickJumper: true,
      showTotal: total => `共 ${total} 条`,
      current: 1,
      total: null,
      pageSize: 20,
    },

    paginationWithAccount: {
      showSizeChanger: true,
      showQuickJumper: true,
      showTotal: total => `共 ${total} 条`,
      current: 1,
      total: null,
      pageSize: 20,
    },
  },

  subscriptions: {
    setup ({ dispatch, history }) {
      history.listen(location => {
        if (location.pathname === '/userGroups') {
          //初始化用戶組列表
          dispatch({
            type: 'queryUserG',
            payload: {"currentPage":1, "pageSize": 20},
          });
          // 加載用戶組類型
          dispatch({
            type: 'initGroupType',
            payload: {},
          });
          //初始化用戶角色列表
          dispatch({
            type: 'queryUserR',
            payload: {
              "currentPage": 1,
              "pageSize": 10000,
              "queryData": {},
              "sorter": {"sortField": "roleId","sortDirection": "ASC"}
            },
          });

          //初始化树根节点
          dispatch({
            type: 'initTree',
            payload: {},
          });

        }
      })
    },
  },

  effects: {
    // query
    *initGroupType({payload}, {call, put}){
      yield put({type: 'showLoading'})
      const data = yield call(loadGroupTypes, payload)
      if (data) {
        yield put({
          type: 'loadGroupTypeSuccess',
          payload: {
            initGroupTypes: data.result.rows,
          },
        })
        yield put({type: 'HideLoading'})
      } else {
        yield put({type: 'HideLoading'})
        let message = {message: '获取数据失败！'}
        throw (message)
      }

    },

    *queryUserG ({payload}, {call, put}) {
      yield put({type: 'showLoading'})
      let {currentPage, pageSize, queryData, sorter} = payload;
      const data = yield call(queryUserGroups, payload)
      if (data) {
        yield put({
          type: 'querySuccess',
          payload: {
            userGroupList: data.result.rows,
            pagination: {
              current: Number(currentPage),
              pageSize: Number(pageSize),
              total: data.result.totalNum,
            },
            sorter,
            loading: false,
            queryData,
          },
        })
      } else {
        yield put({type: 'HideLoading'})
        let message = {message: '获取数据失败！'}
        throw (message)
      }
    },
    *queryUserR ({payload}, {call, put}) {
      yield put({type: 'showLoading'})
      const data = yield call(getRoleList, payload)
      if (data) {
        yield put({
          type: 'initUserRoles',
          payload: {
            userRoles: data.result.rows,
          },
        })
      } else {
        yield put({type: 'HideLoading'})
        let message = {message: '获取数据失败！'}
        throw (message)
      }
    },

    *addUserGroup({payload}, {call, put}) {
      yield put({type: 'showLoading'})
      let {updateData} = payload;
      const data = yield call(addUserGroups, updateData)
      if (data && data.success === true) {
        yield put({type: 'refreshUserGroup', payload})
        yield put({type: 'HideLoading'});
      } else if(data){
        yield put({type: 'HideLoading'})
        let message = {message: data.message}
        throw (message)
      } else {
        yield put({type: 'HideLoading'})
        let message = {message: '获取数据失败！'}
        throw (message)
      }
    },

    *removeUserGroup({payload}, {call, put}) {
      yield put({type: 'showLoading'})
      let {selectedRowKeys} = payload;
      const data = yield call(removeUserGroups, selectedRowKeys)
      if (data) {
        yield put({type: 'refreshUserGroup', payload})
        yield put({type: 'HideLoading'})
      } else {
        yield put({type: 'HideLoading'})
        let message = {message: '获取数据失败！'}
        throw (message)
      }
    },

    *refreshUserGroup({payload}, {call, put}){
      let {pagination,queryData,defaultSorter} = payload;
      //refresh
      const data1 = yield call(queryUserGroups, {currentPage: Number(pagination.current),
        pageSize: Number(pagination.pageSize), queryData, sorter: defaultSorter});
      if(data1 && data1.success ===true){
        yield put({
          type: 'querySuccess',
          payload: {
            userGroupList: data1.result.rows,
            pagination: {
              current: Number(pagination.current),
              pageSize: Number(pagination.pageSize),
              total: data1.result.totalNum,
            },
            sorter: defaultSorter,
            loading: false,
            queryData,
          },
        })
      }else {
        yield put({type: 'HideLoading'})
        let message = {message: '获取数据失败！'}
        throw (message)
      }
    },

    *retrieveUserGroup({payload}, {call, put}){
      let {groupId} = payload;
      const data = yield call(retrieveUserGroups, {groupId});
      if(data && data.success ===true){
        let groupDetail = data.result;
        yield put({
          type: 'loadGroupDetail',
          payload: {groupDetail}
        });
        if(groupDetail.groupType && groupDetail.groupType == 'GROUP_DYNAMIC'){
          yield put({
            type: 'urlShown',
            payload: {}
          });
        }


      }
    },

    *retrieveUserGroupPri({payload}, {call, put}){
      let {groupId} = payload;
      const data = yield call(retrieveUserGroupPrivileges, {groupId});
      if(data && data.success ===true){
        let groupDetail = data.result.rows;
        yield put({
          type: 'loadGroupPrivilegeDetail',
          payload: {groupId, groupDetail}
        });
      }
    },


    *updateUserGroupPrivileges({payload}, {call, put}){
      yield put({type: 'showLoading'})
      let {groupId, updateData} = payload;
      const data = yield call(updateUserGroupPrivileges, {groupId, updateData});
      if(data && data.success ===true){
        yield put({type: 'refreshUserGroup', payload})
        yield put({type: 'HideLoading'});
      }else {
        yield put({type: 'HideLoading'})
        let message = {message: '获取数据失败！'}
        throw (message)
      }
    },

    // for user account .
    *initTree({payload}, {call, put}){
      const data = yield call(findOrgTree,{});
      if(data){
        yield put({
          type: 'initTreeData',
          payload: {
            treeData: data,
          },
        })
      }
    },


    *queryGroupAccount ({payload}, {call, put}) {
      yield put({type: 'showRightLoading'})
      let {currentPage, pageSize, queryData, sorter} = payload;
      const data = yield call(queryGroupAccounts, payload)
      if (data) {
        yield put({
          type: 'queryAccountSuccess',
          payload: {
            accountList: data.result.rows,
            pagination: {
              current: Number(currentPage),
              pageSize: Number(pageSize),
              total: data.result.totalNum,
            },
            sorter,
            loading: false,
            queryData,
          },
        })
      } else {
        yield put({type: 'HideRightLoading'})
        let message = {message: '获取数据失败！'}
        throw (message)
      }
    },

    *shRowDetails ({payload}, {call, put}){
      yield put({type: 'showRightLoading'})
      let {userIdentity } = payload;
      const data = yield call(showDetails, userIdentity);
      if(data && data.success ===true) {
        yield put({type: 'showAccountDetailsSuccess', payload: data.result})
      } else {
        yield put({type: 'HideRightLoading'})
        let message = {message: '获取数据失败！'}
        throw (message)
      }

    },

    *addUserRows({payload}, {call, put}){
      let {userRowKeys, groupId } = payload;
      yield put({type: 'showRightLoading'})
      const data = yield call(addUserRows, {data: userRowKeys, groupId})
      if(data && data.success ===true) {
        yield put({type: 'clearSelectAccountRows'})
        //refresh
        yield put({type: 'refreshAccount', payload})

      } else {
        yield put({type: 'HideRightLoading'})
        let message = {message: '获取数据失败！'}
        throw (message)
      }
    },


    *deleteUserRows({payload}, {call, put}){
      let {selectedRowKeys, groupId } = payload;
      yield put({type: 'showRightLoading'})
      const data = yield call(deleteUserRows, {data: selectedRowKeys, groupId})
      if(data && data.success ===true) {
        yield put({type: 'clearSelectAccountRows'})
        //refresh
        yield put({type: 'refreshAccount', payload})

      } else {
        yield put({type: 'HideRightLoading'})
        let message = {message: '获取数据失败！'}
        throw (message)
      }
    },

    *refreshAccount({payload}, {call, put}){
      let {pagination,queryData,defaultSorter, groupId} = payload;
      //refresh
      const data1 = yield call(queryGroupAccounts, {currentPage: Number(pagination.current),
        pageSize: Number(pagination.pageSize), queryData, sorter: defaultSorter, groupId});
      if(data1 && data1.success ===true){
        yield put({
          type: 'queryAccountSuccess',
          payload: {
            accountList: data1.result.rows,
            pagination: {
              current: Number(pagination.current),
              pageSize: Number(pagination.pageSize),
              total: data1.result.totalNum,
            },
            sorter: defaultSorter,
            loading: false,
            queryData,
          },
        })
      }else {
        yield put({type: 'HideRightLoading'})
        let message = {message: '获取数据失败！'}
        throw (message)
      }
    },






  },


  reducers: {

    loadGroupTypeSuccess (state, {payload}){
      let {initGroupTypes} = payload;
      return { ...state, initGroupTypes};
    },


    querySuccess (state, {payload}) {
      const {userGroupList, pagination, queryData, sorter} = payload
      return {
        ...state,
        userGroupList,
        paginationWithGroup: {
          ...state.paginationWithGroup,
          ...pagination,
        },
        loading: false,
        defaultSorterWithGroup: sorter,
        queryDataWithGroup: queryData,

      }
    },

    initUserRoles(state, {payload}){
      let {userRoles} = payload;
      return {...state, userRoles }
    },


    loadGroupDetail(state, {payload}){
      let {groupDetail} = payload;
      return {...state, groupDetail }
    },

    loadGroupPrivilegeDetail(state, {payload}){
      let {groupId, groupDetail} = payload;
      let privileges = [];
      console.log('groupDetail')
      console.log(groupDetail)
      if(groupDetail){
        groupDetail.map(iem => {
          privileges.push(iem.roleId);
        })
      }
      console.log(privileges)
      return {...state, groupPrivilegeDetail: {groupId, privileges} }
    },

    initTreeData(state, {payload}){
      let {treeData} = payload;
      return {...state, initTreeData: treeData}
    },





    //for user group.
    clearSelectRows(state){
      return {...state, selectedRowKeys: []}
    },


    showDetailsSuccess(state, {payload}){
      return {...state, groupDetail: payload, modalVisible: true, loading: false}
    },


    selectedRows (state, {payload}){
      let {selectedRowKeys} = payload;
      return {...state, selectedRowKeys}
    },





    // for user account
    clearAccountSelectRows(state){
      return {...state, selectedAccountKeys: []}
    },
    showAccountDetailsSuccess(state, {payload}){
      return {...state, accountDetail: payload, modalAccountVisible: true, loadingRight: false}
    },


    hideAccountModal (state) {
      console.log("modalAccountVisible: false")
      return {...state, modalAccountVisible: false}
    },

    selectedAccountRows (state, {payload}){
      let {selectedRowKeys} = payload;
      return {...state, selectedAccountKeys: selectedRowKeys}
    },


    clearSelectAccountRows(state){
      return {...state, selectedAccountKeys: []}
    },

    queryAccountSuccess (state, {payload}) {
      const {accountList, pagination, queryData, sorter} = payload
      return {
        ...state,
        accountList,
        paginationWithAccount: {
          ...state.pagination,
          ...pagination,
        },
        loadingRight: false,
        defaultSorterWithAccount: sorter,
        queryDataWithAccount:queryData,

      }
    },


    // for all.

    showLoading (state) {
      return {...state, loading: true}
    },

    HideLoading (state) {
      return {...state, loading: false}
    },


    showRightLoading (state) {
      return {...state, loadingRight: true}
    },

    HideRightLoading (state) {
      return {...state, loadingRight: false}
    },

    hideModal (state) {
      return {...state, modalVisible: false}
    },


    showModal (state) {
      return {...state, modalVisible: true}
    },

    showRight (state, {payload}) {
      let { groupId } = payload;
      return {...state, showRight: true, selectedGroup:{groupId}}
    },

    hideRight (state) {
      return {...state, showRight: false}
    },

    groupEditModel (state) {
      return {...state, groupModalStatus: {editDisabled: true}}
    },

    groupAddModel (state) {
      return {...state, groupModalStatus: {editDisabled: false}}
    },


    showPrivilegeModal (state){
      return {...state, modalPrivilegeVisible:true }
    },

    hidePrivilegeModal (state){
      return {...state, modalPrivilegeVisible: false }
    },

    urlShown (state) {
      return {...state, groupModalStatus: {...state.groupModalStatus, urlShown: true}}
    },

    urlHide (state) {
      return {...state, groupModalStatus: {...state.groupModalStatus, urlShown: false}}
    },

    oldValueOfUrl (state, {payload} ) {
      let {oldValueOfUrl} = payload;
      return {...state, groupModalStatus: {...state.groupModalStatus, oldValueOfUrl: oldValueOfUrl}}
    },


  }





}
