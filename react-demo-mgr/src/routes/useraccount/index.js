/**
 * Created by STZHANG on 2017/5/25.
 */

import React from 'react'
import { Row, Col, Button, message, Modal } from 'antd'
import PropTypes from 'prop-types'
import { routerRedux } from 'dva/router'
import { connect } from 'dva'
import List from './List'
import Filter from './Filter'
import DetailModal from './Modal'
import UserSelectSearch from './UserSelectSearch'
import {findOrgTree, searchUsers } from '../../services/useraccount'
const ButtonGroup = Button.Group;
const confirm = Modal.confirm;
import classnames from 'classnames';
import styles from './List.less';

const UserAccount = ({ location, dispatch, userAccount }) => {
  const { accountList, pagination, queryData,loading, defaultSorter, modalVisible, selectedRowKeys, accountDetail, initTreeData } = userAccount
  const { pageSize } = pagination

  const filterProps = {
    retrieveDepart: function (value) {
      console.log("start to search org...")
      return findOrgTree({"orgCode": value});
    },
    onFilterChange: function (filterFields) {
      console.log(filterFields)
      dispatch({
        type: 'userAccount/query',
        payload: {
          currentPage: 1,
          pageSize: pageSize,
          sorter: defaultSorter,
          queryData: filterFields
        },
      })

    },
    initTreeData,
  }

  const listProps = {
    accountList,
    pagination,
    loading,
    selectedRowKeys,
    handleShowDetail: function (v) {
      console.log('show detail: '+ v)
      dispatch({
        type: 'userAccount/shRowDetails',
        payload: {
          userIdentity: {"id": v},
        },
      });
    },
    onChange (page, filters, sorter) {
      dispatch({
        type: 'userAccount/query',
        payload: {
          currentPage: page.current,
          pageSize: page.pageSize,
          sorter: {
            sortField: sorter.columnKey,
            sortDirection: sorter.order,
          },
          queryData: queryData
        },
      })
    },

    handleRowsSelected :function (selectedKeys) {
        dispatch({
          type: 'userAccount/selectedRows',
          payload: {selectedRowKeys: selectedKeys},
        })
    }
  }


  const userSearchProps = {
    fetchUserFunc: function (value) {
         return searchUsers([value]);
    },
    handleOnChange: function (userKeys) {
      if(userKeys.length == 0){
        message.warn("请选择要添加的账户")
        return false;
      }
      const  userRowKeys = [];
      userKeys.map((item) =>{
          let k = item.key;
          let ar = k.split("$");

          userRowKeys.push({"userId": ar[0], "accountType": ar[1]});
        });

      dispatch({
        type: 'userAccount/addUserRows',
        payload: {userRowKeys,pagination,queryData,defaultSorter},
      });
      message.success("添加用户成功");
      return true;
    },

  }


  const modalProps = {
    item: accountDetail,
    visible: modalVisible,
    maskClosable: true,
    title: '用户详情',
    wrapClassName: 'vertical-center-modal',
    handleCancel () {
      dispatch({
        type: 'userAccount/hideModal',
      })
    }
  }

  const handleRemove = function() {
    console.log(selectedRowKeys)
    if(selectedRowKeys.length == 0){
      message.warn("请选择要删除的账户")
      return false;
    }
    confirm({
      title: '删除确认?',
      content: `您确认要删除这${selectedRowKeys.length}条记录吗?`,
      onOk() {
        dispatch({
          type: 'userAccount/deleteRows',
          payload: {selectedRowKeys,pagination,queryData,defaultSorter},
        });
      },
      onCancel() {
        return false;
      },
    });


  }
  const handleEnabledRow = function() {
    if(selectedRowKeys.length == 0){
      message.warn("请选择要启用的账户")
      return false;
    }
    confirm({
      title: '删除确认?',
      content: `您确认要启用这${selectedRowKeys.length}条记录吗?`,
      onOk() {
        dispatch({
          type: 'userAccount/enabledRows',
          payload: {selectedRowKeys,pagination,queryData,defaultSorter},
        });
      },
      onCancel() {
        return false;
      },
    });
  }

  const handleDisabledRow = function() {
    if(selectedRowKeys.length == 0){
      message.warn("请选择要停用的账户")
      return false;
    }
    confirm({
      title: '删除确认?',
      content: `您确认要停用这${selectedRowKeys.length}条记录吗?`,
      onOk() {
        dispatch({
          type: 'userAccount/disabledRows',
          payload: {selectedRowKeys,pagination,queryData,defaultSorter},
        });
      },
      onCancel() {
        return false;
      },
    });
  }

  return (
    <div className="content-inner">
      <Filter {...filterProps} />
      <div className={classnames(styles.antSearchResult)}>
        <div style={{margin: 10}}>
          <ButtonGroup style={{marginLeft: 1}}>
            <Button icon="check" type="primary" className="" onClick={handleEnabledRow}>启用</Button>
            <Button icon="shrink" className="" onClick={handleDisabledRow}>停用</Button>
          </ButtonGroup>
          <Button icon="close" type="danger" style={{marginLeft: 10}} className="" onClick={handleRemove}>删除</Button>
          <UserSelectSearch {...userSearchProps}/>
        </div>

        <List {...listProps} />
      </div>

      {modalVisible && <DetailModal {...modalProps} />}

    </div>
  )

}

UserAccount.propTypes = {
  userAccount: PropTypes.object,
  location: PropTypes.object,
  dispatch: PropTypes.func,
  loading: PropTypes.bool,
}

export default connect(({ userAccount }) => ({ userAccount }))(UserAccount)
