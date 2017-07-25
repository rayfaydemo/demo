/**
 * Created by STZHANG on 2017/5/25.
 */
import { request, config } from '../utils'
const { api } = config
const { query_accounts, find_org_tree, show_accounts, add_accounts, search_users, remove_accounts, enable_accounts, disable_accounts } = api

export async function query (params) {
  return request({
    url: query_accounts,
    method: 'POST',
    data: params,
    withCredential: true,
  })
}
export async function showDetails (params) {
  return request({
    url: show_accounts,
    method: 'GET',
    data: params,
    withCredential: true,
  })
}


export async function findOrgTree (params) {
  console.log(params)
  return request({
    url: find_org_tree,
    method: 'GET',
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


export async function searchUsers (params) {
  console.log(params)
  return request({
    url: search_users,
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
  return request({
    url: add_accounts,
    method: 'POST',
    data: params,
    withCredential: true,
  })
}


export async function deleteRow (params) {
  console.log(params)
  return request({
    url: remove_accounts,
    method: 'POST',
    data: params,
    withCredential: true,
  })
}

export async function enableRow (params) {
  console.log(params)
  return request({
    url: enable_accounts,
    method: 'POST',
    data: params,
    withCredential: true,
  })
}

export async function disableRow (params) {
  console.log(params)
  return request({
    url: disable_accounts,
    method: 'POST',
    data: params,
    withCredential: true,
  })
}


