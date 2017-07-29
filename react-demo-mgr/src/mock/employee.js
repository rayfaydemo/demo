const Mock = require('mockjs')
const config = require('../utils/config')

const { apiPrefix, defaultPageSize } = config

const Employee = Mock.mock({
  'data|80-100': [
    {
      id: '@id',
      name: '@cname',
      telNumber: /^1[34578]\d{9}$/,
      address: '@county(true)',
      'sex|+1': [
        '1',
        '0',
      ],
    },
  ],
})

let database = Employee.data

module.exports = {
  [`GET ${apiPrefix}/getEmployeeTableDataSource`] (req, res) {
    const { query } = req
    let { pageSize, pageNumber, name } = query
    pageSize = pageSize || defaultPageSize
    pageNumber = pageNumber || 1

    let newData = database
    if (name) {
      newData = newData.filter((item) => {
        console.log(item.name)
        return item.name.includes(name)
      })
    }
    let start = pageNumber * pageSize
    let end = Number(start) + Number(pageSize)

    console.log(start, end)
    res.status(200).json({
      success: true,
      rows: newData.slice(start, end),
      rowsTotal: newData.length,
    })
  },
}
