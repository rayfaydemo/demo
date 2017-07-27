const Mock = require('mockjs')
const config = require('../utils/config')

const { apiPrefix, defaultPageSize } = config

const Employee = Mock.mock({
  'data|80-100': [
    {
      id: '@id',
      name: '@cname',
      phone: /^1[34578]\d{9}$/,
      'age|11-99': 1,
      address: '@county(true)',
      'isMale|+1': [
        '男',
        '女',
      ],
      email: '@email',
      createTime: '@datetime',
    },
  ],
})

let database = Employee.data

module.exports = {
  [`GET ${apiPrefix}/getEmployeeTableDataSource`] (req, res) {
    const { query } = req
    let { pageSize, currentPage, name } = query
    pageSize = pageSize || defaultPageSize
    currentPage = currentPage || 1

    let newData = database
    if (name) {
      newData = newData.filter((item) => {
        console.log(item.name)
        return item.name.includes(name)
      })
    }
    let start = currentPage * pageSize
    let end = Number(start) + Number(pageSize)

    console.log(start, end)
    res.status(200).json({
      success: true,
      result: {
        rows: newData.slice(start, end),
        totalNum: newData.length,
      },
    })
  },
}
