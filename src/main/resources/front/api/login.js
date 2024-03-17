function loginApi(data) {
  return $axios({
    url: '/employee/login',
    method: 'post',
    data,
  });
}

function logoutApi(){
  return $axios({
    'url': '/employee/logout',
    'method': 'post',
  })
}

function userLoginApi(data) {
  return $axios({
    url: '/user/login',
    method: 'post',
    data,
  });
}

function sendMsg(data) {
  return $axios({
    url: '/user/sendMsg',
    method: 'post',
    data,
  });
}