/**
 * Created by huangzy on 2017/5/18.
 */
import { request, config } from '../utils'
const { api } = config
const { role_type, query_role, save_role, update_role, get_role, del_role, get_rolePrivileges, save_rolePrivileges } = api

export async function getRoleType (params) {
  return request({
    url: role_type,
    method: 'get',
    withCredential: true,
  })
}

export async function getRoleList (params) {
  let currentPage = params.currentPage ? params.currentPage  : 1
  let pageSize = params.pageSize ? params.pageSize : 10
  let queryData = {
    queryData: {},
    sorter: {},
    currentPage,
    pageSize,
  }

  if (params.sort) {
    queryData.sorter = params.sort
  }

  if (params.queryData) {
    queryData.queryData = params.queryData
  }

  return request({
    url: query_role,
    method: 'post',
    data: queryData,
    withCredential: true,
  })
}


export async function saveRole (params) {
  let url
  if (params.edit) {
    url = update_role
  } else {
    url = save_role
  }

  return request({
    url,
    method: 'post',
    data: params.data,
    withCredential: true,
  })
}


export async function getRole (params) {
  return request({
    url: get_role.replace('/:roleId', params),
    method: 'get',
    withCredential: true,
  })
}

export async function deleteRole (params) {
  return request({
    url: del_role,
    method: 'post',
    data: params,
    withCredential: true,
  })
}


export async function getRolePrivileges (params) {
  return request({
    url: get_rolePrivileges.replace('/:roleId', params),
    method: 'get',
    withCredential: true,
  })
}

export async function saveRolePrivileges (params) {
  return request({
    url: save_rolePrivileges.replace('/:roleId', params.roleId),
    method: 'post',
    data: params.selectTreeNode,
    withCredential: true,
  })
}
