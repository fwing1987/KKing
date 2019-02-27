import { axios } from '@/utils/request'

export function getRoleList (params) {
  return axios({
    url: '/sys/role/list',
    method: 'post',
    data: params
  })
}

export const addRole = (params) => {
  return axios({
    url: '/sys/role/add',
    data: {
      ...params
    },
    method: 'post'
  })
}

export const updateRole = (params) => {
  return axios.request({
    url: '/sys/role/update',
    data: {
      ...params
    },
    method: 'post'
  })
}

export const deleteRole = (id) => {
  return axios.request({
    url: '/sys/role/delete',
    params: {
      id
    },
    method: 'get'
  })
}

export const editRolePermssion = (params) => {
  return axios.request({
    url: '/sys/role/perm/update',
    data: {
      ...params
    },
    method: 'post'
  })
}
