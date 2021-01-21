import Vue from 'vue'
import Router from 'vue-router'
import type from '@/components/shop/ProductType'
import Home from '@/components/common/Home'
import dashboard from '@/components/shop/Dashboard'
import brand from '@/components/shop/Brand'
import attribute from '@/components/shop/Attribute'
import Addproduct from '@/components/shop/Addproduct'
import Test from '@/components/shop/Test'
import Product from '@/components/shop/Product'

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
        {
          path: '/Addproduct',
          component: Addproduct,
          name: "Addproduct",
          meta: { title: '商品新增' }
        },
        {
          path: '/Product',
          component: Product,
          name: "Product",
          meta: { title: '商品列表' }
        },
        {
          path: '/Test',
          component: Test,
          name: "Test",
          meta: { title: '测试' }
        },

      ]
    },
  ]
})
