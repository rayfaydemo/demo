/**
 * Created by STZHANG on 2017/5/25.
 */
import {query,enableRow,deleteRow,disableRow, addUserRows,showDetails, findOrgTree } from '../../services/useraccount'

export default {
  namespace: 'userAccount',
  state: {
    accountList: [],
    searchedUsers:[],
    accountDetail:{},
    initTreeData:[],
    selectedRowKeys: [],
    queryData: {},
    modalVisible: false,
    loading: false,
    defaultSorter:{"sortField": "userId","sortDirection": "ASC"},
    pagination: {
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
        if (location.pathname === '/userAccounts') {
          dispatch({
            type: 'query',
            payload: {"currentPage":1, "pageSize": 20},
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
    *query ({payload}, {call, put}) {
      console.log("2323")
      yield put({type: 'showLoading'})
      let {currentPage, pageSize, queryData, sorter} = payload;
      const data = yield call(query, payload)
      if (data) {
        yield put({
          type: 'querySuccess',
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
        yield put({type: 'HideLoading'})
        let message = {message: '获取数据失败！'}
        throw (message)
      }
    },


    *shRowDetails ({payload}, {call, put}){
      let {userIdentity } = payload;
      const data = yield call(showDetails, userIdentity);
      if(data && data.success ===true) {
        yield put({type: 'showDetailsSuccess', payload: data.result})
      } else {
        let message = {message: '获取数据失败！'}
        throw (message)
      }

    },


    *addUserRows({payload}, {call, put}){
      let {userRowKeys } = payload;
      yield put({type: 'showLoading'})
      const data = yield call(addUserRows, userRowKeys)
      if(data && data.success ===true) {
        yield put({type: 'clearSelectRows'})
        //refresh
        yield put({type: 'refresh', payload})

      } else {
        yield put({type: 'HideLoading'})
        let message = {message: '获取数据失败！'}
        throw (message)
      }
    },


    *deleteRows({payload}, {call, put}){
        let {selectedRowKeys } = payload;
        yield put({type: 'showLoading'})
        const data = yield call(deleteRow, selectedRowKeys)
        if(data && data.success ===true) {
          yield put({type: 'clearSelectRows'})
          //refresh
          yield put({type: 'refresh', payload})

        } else {
          yield put({type: 'HideLoading'})
          let message = {message: '获取数据失败！'}
          throw (message)
      }
    },

    *refresh({payload}, {call, put}){
        let {pagination,queryData,defaultSorter} = payload;
        //refresh
        const data1 = yield call(query, {currentPage: Number(pagination.current),
          pageSize: Number(pagination.pageSize), queryData, sorter: defaultSorter});
        if(data1 && data1.success ===true){
          yield put({
            type: 'querySuccess',
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
          yield put({type: 'HideLoading'})
          let message = {message: '获取数据失败！'}
          throw (message)
        }
    },

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

    *enabledRows({payload}, {call, put}){
      let {selectedRowKeys} = payload;
      yield put({type: 'showLoading'})
      const data = yield call(enableRow, selectedRowKeys)
      if(data && data.success ===true) {
        yield put({type: 'clearSelectRows'})
        //refresh
        yield put({type: 'refresh', payload})

      } else {
        yield put({type: 'HideLoading'})
        let message = {message: '获取数据失败！'}
        throw (message)
      }
    },

    *disabledRows({payload}, {call, put}){
      let {selectedRowKeys, queryData} = payload;
      //showLoading
      yield put({type: 'clearSelectRows'})
      const data = yield call(disableRow, selectedRowKeys)
      if(data && data.success ===true) {
        yield put({type: 'clearSelectRows'})
        //refresh
        yield put({type: 'refresh', payload})

      } else {
        yield put({type: 'HideLoading'})
        let message = {message: '获取数据失败！'}
        throw (message)
      }
    },


  },

  reducers: {

    initTreeData(state, {payload}){
      let {treeData} = payload;
      console.log('treeData')
      console.log(treeData)
      return {...state, initTreeData: treeData}
    },

    clearSelectRows(state){
      return {...state, selectedRowKeys: []}
    },

    showDetailsSuccess(state, {payload}){
      return {...state, accountDetail: payload, modalVisible: true}
    },

    showLoading (state) {
      return {...state, loading: true}
    },

    HideLoading (state) {
      return {...state, loading: false}
    },

    hideModal (state) {
      return {...state, modalVisible: false}
    },


    selectedRows (state, {payload}){
      let {selectedRowKeys} = payload;
      return {...state, selectedRowKeys}
    },

    querySuccess (state, {payload}) {
      const {accountList, pagination, queryData, sorter} = payload
      return {
        ...state,
        accountList,
        pagination: {
          ...state.pagination,
          ...pagination,
        },
        loading: false,
        defaultSorter: sorter,
        queryData,

      }
    },
  }



}
