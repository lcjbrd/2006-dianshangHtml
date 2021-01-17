import Vue from 'vue'
import Router from 'vue-router'
import type from '@/components/shop/ProductType'
import Home from '@/components/common/Home'
import dashboard from '@/components/shop/Dashboard'
import brand from '@/components/shop/Brand'
import attribute from '@/components/shop/Attribute'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      redirect: '/dashboard'
    },
    {
      path: '/',
      component: Home,
      meta: { title: '系统首页' },
      children: [
        {
          path: '/dashboard',
          component: dashboard,
          name:"dashboard",
          meta: { title: '系统首页' }
        },
        {
          path: '/type',
          component: type,
          name: "type",
          meta: { title: '商品分类' }
        },
        {
          path: '/brand',
          component: brand,
          name: "brand",
          meta: { title: '商品品牌' }
        },
        {
          path: '/attribute',
          component: attribute,
          name: "attribute",
          meta: { title: '商品属性' }
        },
      ]
    },
  ]
})
