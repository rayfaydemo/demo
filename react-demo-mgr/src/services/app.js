import { request, config } from '../utils'

const { api, serviceDomain } = config
const { user, userLogout, userLogin, menus } = api

export async function login (params) {
  return request({
    url: userLogin,
    method: 'post',
    data: params,
  })
}

export async function logout (params) {
  return request({
    url: userLogout,
    method: 'get',
    data: params,
  })
}

export async function query (params) {
  return request({
    url: user.replace('/:id', ''),
    method: 'get',
    data: params,
    withCredential: true,
  }).then((userData) => {
    if (userData.success && userData.result) {
      userData.user = userData.result
    }
    return userData
  })
}

export async function getMenus () {
  return request({
    url: menus,
    method: 'get',
    withCredential: true,
  })
}

