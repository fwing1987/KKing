import { axios } from '@/utils/request'

export function getDeptList (params) {
  return axios({
    url: '/sys/dept/list',
    method: 'post',
    data: params
  })
}

export const addDept = (params) => {
  return axios({
    url: '/sys/dept/add',
    data: {
      ...params
    },
    method: 'post'
  })
}

export const updateDept = (params) => {
  return axios.request({
    url: '/sys/dept/update',
    data: {
      ...params
    },
    method: 'post'
  })
}

export const deleteDept = (id) => {
  return axios.request({
    url: '/sys/dept/delete',
    data: {
      id
    },
    method: 'post'
  })
}

export const getDeptWithRoleStatus = (params) => {
  return axios.request({
    url: '/sys/dept/listRole',
    data: {
      ...params
    },
    method: 'post'
  })
}
