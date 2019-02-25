import { axios } from '@/utils/request'

export function getMenuList (params) {
  return axios({
    url: '/sys/menu/list',
    method: 'post',
    data: params
  })
}

export const addMenu = (params) => {
  return axios({
    url: '/sys/menu/add',
    data: {
      ...params
    },
    method: 'post'
  })
}

export const updateMenu = (params) => {
  return axios.request({
    url: '/sys/menu/update',
    data: {
      ...params
    },
    method: 'post'
  })
}

export const deleteMenu = (id) => {
  return axios.request({
    url: '/sys/menu/delete',
    params: {
      id
    },
    method: 'get'
  })
}