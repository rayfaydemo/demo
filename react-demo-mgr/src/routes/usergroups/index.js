import React from 'react'
import { Row, Col, Button, message, Modal } from 'antd'
import PropTypes from 'prop-types'
import { routerRedux } from 'dva/router'
import { connect } from 'dva'

import GroupList from './GroupList'
import GroupFilter from './GroupFilter'
import UserFilter from './UserFilter'
import UserList from './UserList'

import UserGroupModal from './UserGroupModal'
import UserGroupPrivilegeModal from './GroupPrivilegesModal'
import UserDetailModal from '../useraccount/Modal'
import UserSelectSearch from '../useraccount/UserSelectSearch'
import { findOrgTree } from '../../services/useraccount'
import { searchUserAccounts } from '../../services/usergroups'
const ButtonGroup = Button.Group;
const confirm = Modal.confirm;
import classnames from 'classnames';
import styles from './List.less';


const UserGroupPage = ({ location, dispatch, userGroup }) => {
  const { userGroupList, paginationWithGroup, groupPrivilegeDetail, initGroupTypes, modalPrivilegeVisible, queryDataWithGroup, defaultSorterWithGroup, userRoles, modalVisible, groupDetail, selectedRowKeys,  loading, showRight} = userGroup;
  const { accountList, paginationWithAccount, queryDataWithAccount, defaultSorterWithAccount, modalAccountVisible, selectedAccountKeys, accountDetail, initTreeData, loadingRight } = userGroup
  const { pageSize: groupPageSize } = paginationWithGroup;
  const { pageSize: accountPageSize } = paginationWithGroup;
  const {selectedGroup, groupModalStatus} = userGroup;
  const {groupId: selectedGroupId} = selectedGroup;


  const filterGroupProps = {
    onFilterChange: function (filterFields) {
      console.log(filterFields)
      dispatch({
        type: 'userGroup/queryUserG',
        payload: {
          currentPage: 1,
          pageSize: groupPageSize,
          sorter: defaultSorterWithGroup,
          queryData: filterFields
        },
      })
    },
    initGroupTypes,
  }

  const listGroupProps = {
    userGroupList,
    pagination: paginationWithGroup,
    loading,
    selectedRowKeys,
    handleEditDetail: function (v) {
      console.log('show detail: '+ v)
      handleEditUserGroup(v);
    },
    onChange (page, filters, sorter) {
      dispatch({
        type: 'userGroup/queryUserG',
        payload: {
          currentPage: page.current,
          pageSize: page.pageSize,
          sorter: {
            sortField: sorter.columnKey,
            sortDirection: sorter.order,
          },
          queryData: filters
        },
      })
    },

    handleRowsSelected :function (selectedKeys) {
      dispatch({
        type: 'userGroup/selectedRows',
        payload: {selectedRowKeys: selectedKeys},
      })
    },

    handleEditAccounts: function (groupId) {
      dispatch({
        type: 'userGroup/showRight',
        payload: {groupId},
      });

      dispatch({
        type: 'userGroup/queryGroupAccount',
        payload: {
          currentPage: 1,
          pageSize: accountPageSize,
          sorter: defaultSorterWithAccount,
          queryData: {},
          groupId: groupId,
        },
      });


    },

    handleEditRoles: function (groupId) {

      handleEditUserGroupPrivileges(groupId)
    },

  };

  const userGroupModal = {
    item: groupDetail,
    visible: modalVisible,
    maskClosable: false,
    title: '编辑用户组',
    wrapClassName: 'vertical-center-modal',
    handleCancel (resetFields) {
      dispatch({
        type: 'userGroup/hideModal',
      })
    },

    handleSaveOk (fields){
      dispatch({
        type: 'userGroup/addUserGroup',
        payload: {updateData: fields, selectedRowKeys: selectedRowKeys, pagination: paginationWithGroup
          , queryData: queryDataWithGroup, defaultSorter: defaultSorterWithGroup},
      })
      dispatch({
        type: 'userGroup/hideModal',
      })

    },
    groupTypeList: initGroupTypes,
    groupModalStatus,
    dispatch

  };



  const handleAddUserGroup = function () {
    //clear current groupDetail
    dispatch({
      type: 'userGroup/loadGroupDetail',
      payload: {groupDetail:{}},
    });
    dispatch({
      type: 'userGroup/groupAddModel',
      payload: {},
    });
    dispatch({
      type: 'userGroup/showModal',
    });
  }

  const handleEditUserGroup = function (groupId) {
    dispatch({
      type: 'userGroup/groupEditModel',
      payload: {},
    });

    dispatch({
      type: 'userGroup/retrieveUserGroup',
      payload: {groupId},
    });
    dispatch({
      type: 'userGroup/showModal',
    });
  }

  const handleEditUserGroupPrivileges = function (groupId) {
    dispatch({
      type: 'userGroup/retrieveUserGroupPri',
      payload: {groupId},
    });
    dispatch({
      type: 'userGroup/showPrivilegeModal',
    });
  }

  const userGroupPrivilegesModal = {
    item: groupPrivilegeDetail,
    visible: modalPrivilegeVisible,
    maskClosable: false,
    title: '编辑用户组授权',
    wrapClassName: 'vertical-center-modal',
    handleCancel (resetFields) {
      dispatch({
        type: 'userGroup/hidePrivilegeModal',
      })
    },

    handleSaveOk (fields){
      dispatch({
        type: 'userGroup/updateUserGroupPrivileges',
        payload: {groupId: fields.groupId, updateData: fields.rolePrivileges, selectedRowKeys: selectedRowKeys, pagination: paginationWithGroup
          , queryData: queryDataWithGroup, defaultSorter: defaultSorterWithGroup},
      })
      dispatch({
        type: 'userGroup/hidePrivilegeModal',
      })

    },
    userRoles

  };


  const accountFilterProps = {
    retrieveDepart: function (value) {
      console.log("start to search org...")
      return findOrgTree({"orgCode": value});
    },
    onFilterChange: function (filterFields) {
      console.log(filterFields)
      dispatch({
        type: 'userGroup/queryGroupAccount',
        payload: {
          currentPage: 1,
          groupId: selectedGroupId,
          pageSize: accountPageSize,
          sorter: defaultSorterWithAccount,
          queryData: filterFields
        },
      })

    },
    initTreeData,
  };

  const listAccountProps = {
    accountList,
    pagination: paginationWithAccount,
    loading: loadingRight,
    selectedRowKeys: selectedAccountKeys,
    handleShowDetail: function (v) {
      console.log('show detail: '+ v)
      dispatch({
        type: 'userGroup/shRowDetails',
        payload: {
          userIdentity: {"id": v},
        },
      });
    },
    onChange (page, filters, sorter) {
      dispatch({
        type: 'userGroup/queryGroupAccount',
        payload: {
          currentPage: page.current,
          pageSize: page.pageSize,
          sorter: {
            sortField: sorter.columnKey,
            sortDirection: sorter.order,
          },
          queryData: filters,
          groupId: selectedGroupId,
        },
      })
    },

    handleRowsSelected :function (selectedKeys) {
      dispatch({
        type: 'userGroup/selectedAccountRows',
        payload: {selectedRowKeys: selectedKeys},
      })
    }
  };


  const userSearchProps = {

    fetchUserFunc: function (value) {
      return searchUserAccounts([value]);
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

        userRowKeys.push(ar[2]);
      });

      dispatch({
        type: 'userGroup/addUserRows',
        payload: {userRowKeys, groupId: selectedGroupId, pagination: paginationWithAccount,queryData: queryDataWithAccount,defaultSorter: defaultSorterWithAccount},
      });
      message.success("添加用户成功");
      return true;
    },

  }




  const modalAccountProps = {
    item: accountDetail,
    visible: modalAccountVisible,
    maskClosable: true,
    title: '用户详情',
    wrapClassName: 'vertical-center-modal',
    handleCancel () {
      dispatch({
        type: 'userGroup/hideAccountModal',
        payload:{},
      })
    }
  }
  // rows
  const handleAccountRowRemove = function() {
    console.log(selectedAccountKeys)
    if(selectedAccountKeys.length == 0){
      message.warn("请选择要移除的账户")
      return false;
    }
    confirm({
      title: '移除确认?',
      content: `您确认要移除这${selectedAccountKeys.length}条记录吗?`,
      onOk() {
        dispatch({
          type: 'userGroup/deleteUserRows',
          payload: {groupId: selectedGroupId, selectedRowKeys: selectedAccountKeys, pagination: paginationWithAccount
            , queryData: queryDataWithAccount, defaultSorter: defaultSorterWithAccount},
        });
      },
      onCancel() {
        return false;
      },
    });

  }


  const handleRowRemove = function() {
    console.log(selectedRowKeys)
    if(selectedRowKeys.length == 0){
      message.warn("请选择要移除的用户组")
      return false;
    }
    confirm({
      title: '删除确认?',
      content: `您确认要删除这${selectedRowKeys.length}条记录吗?`,
      onOk() {
        dispatch({
          type: 'userGroup/removeUserGroup',
          payload: {selectedRowKeys: selectedRowKeys, pagination: paginationWithGroup
            , queryData: queryDataWithGroup, defaultSorter: defaultSorterWithGroup},
        });
      },
      onCancel() {
        return false;
      },
    });

  }


  return (
    <div className="content-inner">
      <Row gutter={6}>
        <Col xl={{ span: showRight? 12 : 24 }} md={{ span: 24 }} >
          <GroupFilter {...filterGroupProps} />
          <div className={classnames(styles.antButtonBar)}>
            <div style={{margin: 2}}>

                <Button icon="check" type="primary" className="margin-right" onClick={handleAddUserGroup} >新增</Button>
                <Button icon="shrink" className=""  onClick={handleRowRemove}>删除</Button>

            </div>
          </div>
          <GroupList {...listGroupProps}/>
        </Col>
        <Col xl={{ span: showRight? 12 : 0 }} md={{  span: showRight? 24 : 0  }} >
          <div className={classnames(styles.antSearchResult)}>
            <UserFilter {...accountFilterProps} />
            <div style={{margin: 10}}>
              <Button icon="close" type="" style={{marginLeft: 1}} className="" onClick={handleAccountRowRemove}>移除</Button>
              <UserSelectSearch {...userSearchProps}/>
            </div>
            <UserList {...listAccountProps} />
          </div>
        </Col>
      </Row>
      {modalAccountVisible && <UserDetailModal {...modalAccountProps} />}
      {modalVisible && <UserGroupModal {...userGroupModal} />}
      {modalPrivilegeVisible && <UserGroupPrivilegeModal {...userGroupPrivilegesModal} />}

    </div>
  )

}

UserGroupPage.propTypes = {
  userGroup: PropTypes.object,
  location: PropTypes.object,
  dispatch: PropTypes.func,
  loading: PropTypes.bool,
}

export default connect(({ userGroup }) => ({ userGroup }))(UserGroupPage)
