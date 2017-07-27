import { request, config } from '../utils'

const { serviceDomain, defaultPageSize } = config

export async function getEmployeeTableDataSource (params) {
  let currentPage = params.currentPage ? params.currentPage - 1 : 0
  let pageSize = params.pageSize ? params.pageSize : defaultPageSize

  let url = `http://localhost:8000/api/v1/getEmployeeTableDataSource?currentPage=${currentPage}&pageSize=${pageSize}`

  if (params.txtEmployeeName) {
    url += `&name=${params.txtEmployeeName}`
  }

  return request({
    url,
    method: 'GET',
    withCredential: true,
  })
}
