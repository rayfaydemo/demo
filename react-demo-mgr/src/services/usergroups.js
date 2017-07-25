/**
 * Created by STZHANG on 2017/6/21.
 */

import { request, config } from '../utils'
const { api } = config
const { query_usergroups, query_role, add_usergroups, remove_usergroups, retrieve_usergroups, query_groupaccounts,
  add_groupaccounts, remove_groupaccounts, search_Account, list_groupTypes, retrieve_groupRoles, update_groupRoles
} = api
export async function queryUserGroups (params) {
  return request({
    url: query_usergroups,
    method: 'POST',
    data: params,
    withCredential: true,
  })
}

export async function getRoleList (params) {
  if (!params.sorter) {
    params.sorter = {};
  }
  if (!params.queryData) {
    params.queryData = {};
  }
  return request({
    url: query_role,
    method: 'post',
    data: params,
    withCredential: true,
  })
}

export async function addUserGroups (params) {
  return request({
    url: add_usergroups,
    method: 'POST',
    data: params,
    withCredential: true,
  })
}

export async function removeUserGroups (params) {
  return request({
    url: remove_usergroups,
    method: 'DELETE',
    data: params,
    withCredential: true,
  })
}

export async function retrieveUserGroups (params) {
  return request({
    url: retrieve_usergroups,
    method: 'GET',
    data: params,
    withCredential: true,
  })
}

//for user account
export async function queryGroupAccounts (params) {
  let {groupId, queryData} = params;
  let query_url = query_groupaccounts.replace(':groupId', groupId);
  return request({
    url: query_url,
    method: 'POST',
    data: params,
    withCredential: true,
  })
}

export async function searchUserAccounts (params) {
  return request({
    url: search_Account+'?threshold=100',
    method: 'POST',
    data: params,
    withCredential: true,
  }).then(function (data) {
    if(data.success) {
      return data.result.rows;
    }else {
      return [];
    }
  })
}


export async function addUserRows (params) {
  console.log(params)
  let {groupId, data} = params;
  let query_url = add_groupaccounts.replace(':groupId', groupId);
  return request({
    url: query_url,
    method: 'POST',
    data: data,
    withCredential: true,
  })
}


export async function deleteUserRows (params) {
  console.log(params)
  let {groupId, data} = params;
  let query_url = remove_groupaccounts.replace(':groupId', groupId);
  return request({
    url: query_url,
    method: 'delete',
    data: data,
    withCredential: true,
  })
}
export async function loadGroupTypes (params) {
  return request({
    url: list_groupTypes,
    method: 'GET',
    data: params,
    withCredential: true,
  })
}


export async function retrieveUserGroupPrivileges (params) {
  return request({
    url: retrieve_groupRoles,
    method: 'GET',
    data: params,
    withCredential: true,
  });
}



export async function updateUserGroupPrivileges (params) {
  let {groupId, updateData} = params;
  let query_url = update_groupRoles.replace(':groupId', groupId);
  return request({
    url: query_url,
    method: 'PUT',
    data: updateData,
    withCredential: true,
  })
}


