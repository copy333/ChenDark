import { createStore } from 'vuex'

export default createStore({
  state: {
    //Authorization: localStorage.getItem('Authorization') ? localStorage.getItem('Authorization') : '', //存储token
    roles: [],  //登录的用户权限
    username: '', //登录用户的 username
    avatar: '', //登录用户的头像
    nickname: '',  //登录用户的昵称
    intro: '' //登录用户的个人简介
  },
  getters:{ 

  },
  mutations: {
    
    //设置登录用户的信息
    setUserInfo(state, data) {
      state.roles = data.roles;
      state.username = data.username;
      state.avatar = data.avatar;
      state.nickname = data.nickname;
      state.intro = data.intro;

      //删除旧的信息
      localStorage.removeItem("avatar");
      localStorage.removeItem("nickname");
      localStorage.removeItem("intro");
      localStorage.removeItem("roles");

      // 存储新的信息
      localStorage.setItem("avatar", data.avatar);
      localStorage.setItem("nickname", data.nickname);
      localStorage.setItem("intro", data.intro);
      localStorage.setItem("roles", data.roles);

    }

  },
  actions: {
  },
  modules: {
  }
})
