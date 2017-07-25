import jsonp from 'jsonp'
import lodash from 'lodash'
import pathToRegexp from 'path-to-regexp'
import { message } from 'antd'
import axios from 'axios'
import qs from 'qs'
import { YQL, CORS, baseURL, prefix } from './config'


axios.defaults.baseURL = baseURL

const fetch = (options) => {
  let {
    method = 'get',
    withCredential = false,
    data,
    fetchType,
    url,
  } = options

  const cloneData = lodash.cloneDeep(data)

  try {
    let domin = ''
    if (url.match(/[a-zA-z]+:\/\/[^/]*/)) {
      domin = url.match(/[a-zA-z]+:\/\/[^/]*/)[0]
      url = url.slice(domin.length)
    }
    const match = pathToRegexp.parse(url)
    url = pathToRegexp.compile(url)(data)
    for (let item of match) {
      if (item instanceof Object && item.name in cloneData) {
        delete cloneData[item.name]
      }
    }
    url = domin + url
  } catch (e) {
    message.error(e.message)
  }
  let activeMenuAsString = localStorage.getItem(`${prefix}navActiveMenu`)
  let activeMenuId = JSON.parse(activeMenuAsString)
  let activeMenuKey = ''
  if (activeMenuId && activeMenuId.key) {
    activeMenuKey = activeMenuId.key
  }


  if (fetchType === 'JSONP') {
    return new Promise((resolve, reject) => {
      jsonp(url, {
        param: `${qs.stringify(data)}&callback`,
        name: `jsonp_${new Date().getTime()}`,
        timeout: 4000,
      }, (error, result) => {
        if (error) {
          reject(error)
        }
        resolve({ statusText: 'OK', status: 200, data: result })
      })
    })
  } else if (fetchType === 'YQL') {
    url = `http://query.yahooapis.com/v1/public/yql?q=select * from json where url='${options.url}?${qs.stringify(options.data)}'&format=json`
    data = null
  }

  switch (method.toLowerCase()) {
    case 'get':
      return axios.get(url, {
        params: cloneData,
        withCredentials: withCredential,
        headers: { 'x-forward-menu-id': activeMenuKey },
      })
    case 'delete':
      return axios.delete(url, {
        data: cloneData,
        withCredentials: withCredential,
        headers: { 'x-forward-menu-id': activeMenuKey },
      })
    case 'post':
      return axios.post(url, cloneData, {
        withCredentials: withCredential,
        headers: { 'x-forward-menu-id': activeMenuKey },
      })
    case 'put':
      return axios.put(url, cloneData, {
        withCredentials: withCredential,
        headers: { 'x-forward-menu-id': activeMenuKey },
      })
    case 'patch':
      return axios.patch(url, cloneData, {
        withCredentials: withCredential,
        headers: { 'x-forward-menu-id': activeMenuKey },
      })
    default:
      return axios(options)
  }
}

export default function request (options) {
  if (options.url && options.url.indexOf('//') > -1) {
    const origin = `${options.url.split('//')[0]}//${options.url.split('//')[1].split('/')[0]}`
    if (window.location.origin !== origin) {
      if (CORS && CORS.indexOf(origin) > -1) {
        options.fetchType = 'CORS'
      } else if (YQL && YQL.indexOf(origin) > -1) {
        options.fetchType = 'YQL'
      } else {
        options.fetchType = 'JSONP'
      }
    }
  }

  return fetch(options).then((response) => {
    const { statusText, status } = response
    let data = options.fetchType === 'YQL' ? response.data.query.results.json : response.data
    return {
      success: true,
      message: statusText,
      status,
      ...data,
    }
  }).catch((error) => {
    const { response } = error
    let msg
    let status
    let otherData = {}
    if (response) {
      const { data, statusText } = response
      otherData = data
      status = response.status
      msg = data.message || statusText
    } else {
      status = 600
      msg = 'Network Error'
    }
    return { success: false, status, message: msg, ...otherData }
  })
}
