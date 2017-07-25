/**
 * Created by STZHANG on 2017/5/13.
 */
import React from 'react';
import FeatureSetConfig from '../../components/common/FeatureSetConfig';
import { connect } from 'dva';
import { routerRedux } from 'dva/router'
import { Form, Modal, message } from 'antd';
const PersonalPage = ({personal, dispatch} ) => {
  const { userInfo } = personal
  const config = {
    type: 'simpleObject',
    initData: function (callback){
      console.log('init data...')
      console.log(userInfo)
      callback(userInfo)
    },

    Update:function(data, callback){
      dispatch({ type: 'personal/updateUser', payload: data })
      callback(data);
    },

    UType: [
      {
        label: '编号',
        name: 'userId',
        type: 'string',
        placeholder: '请输入用戶编号'
      }, {
        label: '姓名',
        name: 'userName',
        type: 'string',
        placeholder: '请输入用戶姓名'
      },
    ],
    operate: [
      {
        text: '保存',
        type: 'update',
        style: {
          'marginLeft': '80px'
        }
      }
    ]
  }



  const table_conf = {

    type: 'tableList',

    // 初始化页面的数据 回调函数传入 items 列表
    initData: function(callback){
        let list = [
          {
            key: 'key',
            title: '李金平出访中华日本省',
            link: 'http://www.baidu.com',
          },  {
            key: 'key2',
            title: '李金平出访中华日本省2',
            link: 'http://www.baidu.com',
          }
        ]
        callback(list);
    },

    // table 表单字段
    columns: [
      {
        title: 'KEY',     // table header 文案
        dataIndex: 'key', // 数据对象内的属性，也做react vdom 的key
        type: 'string',     // table 内显示的类型
        sort: true,         // 是否需要排序
      }, {
        title: '标题',
        dataIndex: 'title',
        type: 'string',
        sort: true,
      }, {
        title: '链接',
        dataIndex: 'link',
        type: 'link'
      }, {
        title: '操作',
        type: 'operate',    // 操作的类型必须为 operate
        btns: [{
          text: 'console输出',
          callback: function(item){
            console.log(item)
          }
        }], // 可选
      }
    ],

    // 可设置的查询字段
    RType:[
      {
        name: 'title',
        label: '唯一标识',
        type: 'string',
        placeholder: '请输入标示名称'
      },{
        name: 'ischange',
        label: '是否过滤',
        type: 'switch',
        defaultValue: false
      }

    ],
    // 查询操作回调
    Retrieve: function(data, callback){

      console.log(data);
      message.info("正在查询..")
      callback(data)
    }

  };

  const FeatureTable = FeatureSetConfig(table_conf);
  // end conf
  const Feature = FeatureSetConfig(config);
  return (
    <div>
      <Feature></Feature>
      <hr/>
      <FeatureTable></FeatureTable>
    </div>
  )
}

const mapStateToProps = function ({ personal, dispath }) {
  return {personal, dispath}
}
export default connect(mapStateToProps)(PersonalPage)
