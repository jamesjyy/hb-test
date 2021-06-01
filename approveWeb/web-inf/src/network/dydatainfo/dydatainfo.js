import {request} from '@/network/request.js';

export function initData(data) {
  return request({
    url: 'dyDatainfo/queryAllByLimit',
    method: 'post',
    data: data
  })
}
export function AdmininitData(data) {
  return request({
    url: 'dyDatainfo/AdminqueryAllByLimit',
    method: 'post',
    data: data
  })
}
export function PtuserinitData(data) {
  return request({
    url: 'dyDatainfo/PtuserqueryAllByLimit',
    method: 'post',
    data: data
  })
}
export function loopinitData(data) {
  return request({
    url: 'dyDatainfo/queryloopAllByLimit',
    method: 'post',
    data: data
  })
}
