/**
 * Created by STZHANG on 2017/5/13.
 */
export default {
  namespace: 'personal',
  state: {
    userInfo: {
      userId: '012922',
      userName: '张世同',
    },
    favorites: [

    ],
  },
  reducers: {
    updateUser (state, {payload}){
      let {userName} = payload;
      let us = {userName: userName + '(修改)'}
      let updateData = {userInfo: { ...payload, ...us}}
      return {...state, ...updateData }
    }
  },
}
