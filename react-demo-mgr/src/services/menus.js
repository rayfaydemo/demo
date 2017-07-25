import { request, config } from '../utils'
const { api } = config
const {mgr_menus, info_menus, save_menus} = api

export async function getMgrMenus (params) {
  return request({
    url: mgr_menus,
    method: 'get',
    withCredential: true,
  })
}

export async function getMgrMenuInfo (params) {
  return request({
    url: info_menus,
    method: 'get',
    data: params,
    withCredential: true,
  })
}

export async function saveMgrMenuInfo (params) {
    return request({
      url: save_menus,
      method: 'POST',
      data: params,
      withCredential: true,
    })
}
