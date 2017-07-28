import { request, config } from '../utils'

const { serviceDomain, defaultPageSize } = config

export async function getEmployeeTableDataSource (params) {
  let currentPage = params.currentPage ? params.currentPage : 1
  let pageSize = params.pageSize ? params.pageSize : defaultPageSize

  let url = `${serviceDomain}/employee/getPageable?pageNumber=${currentPage}&pageSize=${pageSize}`

  if (params.txtEmployeeName) {
    url += `&name=${params.txtEmployeeName}`
  }

  return request({
    url,
    method: 'GET',
    withCredential: true,
  })
}

export async function createEmployee (params) {
  return request({
    url: `${serviceDomain}/employee/add`,
    data: params,
    method: 'POST',
    withCredential: true,
  })
}

export async function updateEmployee (params) {
  return request({
    url: `${serviceDomain}/employee/update`,
    data: params,
    method: 'PUT',
    withCredential: true,
  })
}

export async function deleteEmployee (params) {
  return request({
    url: `${serviceDomain}/employee/delete?id=${params.id}`,
    method: 'DELETE',
    withCredential: true,
  })
}

export async function getEmployee (params) {
  return request({
    url: `${serviceDomain}/employee/get?id=${params.id}`,
    method: 'GET',
    withCredential: true,
  })
}
