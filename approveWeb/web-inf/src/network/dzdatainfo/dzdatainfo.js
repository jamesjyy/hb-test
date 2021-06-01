import {request} from '@/network/request.js';

export function initData(data) {
  return request({
    url: 'dzDatainfo/queryAllByLimit',
    method: 'post',
    data: data
  })
}
export function AdmininitData(data) {
  return request({
    url: 'dzDatainfo/DzAdminqueryAllByLimit',
    method: 'post',
    data: data
  })
}
export function PtuserinitData(data) {
  return request({
    url: 'dzDatainfo/PtuserqueryAllByLimit',
    method: 'post',
    data: data
  })
}
export function loopinitData(data) {
  return request({
    url: 'dzDatainfo/dzqueryloopAllByLimit',
    method: 'post',
    data: data
  })
}
