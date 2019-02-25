import Vue from 'vue'
import router from './router'
import store from './store'

import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import notification from 'ant-design-vue/es/notification'
import { ACCESS_TOKEN } from '@/store/mutation-types'
import { UserLayout, BasicLayout, RouteView, BlankLayout, PageView } from '@/components/layouts'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['login', 'register', 'registerResult'] // no redirect whitelist

const makeRoutesSafe = (routes, deep) => {
  for (var index in routes) {
    var route = routes[index]
    var meta = { title: route.name, icon: route.icon, permissionId: route.permName }
    route.meta = meta
    if (!route.path) {
      route.path = ''
    }
    if (route.children) {
      route.component = PageView
      route.redirect = route.children[0].path
    } else {
      let componentPath = route.component
      route.component = () => import(`@/views/${componentPath}`)
      console.log(route.component)
    }
    if (route.children) {
      makeRoutesSafe(route.children, ++deep)
    }
  }
  return routes
}
export const addRoutes = (newRoutes) => {
  makeRoutesSafe(newRoutes, 0)
  store.commit('SET_ROUTERS',newRoutes)
  router.addRoutes(store.getters.addRouters)
  console.log(store.getters.addRouters)
}

router.beforeEach((to, from, next) => {
  NProgress.start() // start progress bar

  if (Vue.ls.get(ACCESS_TOKEN)) {
    /* has token */
    if (to.path === '/user/login') {
      next({ path: '/dashboard/workplace' })
      NProgress.done()
    } else {
      if (!store.getters.nickname) {
        store
          .dispatch('GetInfo')
          .then(res => {
            // 新增服务器返回的菜单路由
            addRoutes(res.menus)
            const redirect = decodeURIComponent(from.query.redirect || to.path)
            if (to.path === redirect) {
              // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
              next({ ...to, replace: true })
            } else {
              // 跳转到目的路由
              next({ path: redirect })
            }
            // const roles = res.result && res.result.role
            // store.dispatch('GenerateRoutes', { roles }).then(() => {
            //   // 根据roles权限生成可访问的路由表
            //   // 动态添加可访问路由表
            //   router.addRoutes(store.getters.addRouters)
            //   const redirect = decodeURIComponent(from.query.redirect || to.path)
            //   if (to.path === redirect) {
            //     // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
            //     next({ ...to, replace: true })
            //   } else {
            //     // 跳转到目的路由
            //     next({ path: redirect })
            //   }
            // })
          })
          .catch((e) => {
            debugger
            notification.error({
              message: '错误',
              description: '请求用户信息失败，请重试'
            })
            store.dispatch('Logout').then(() => {
              next({ path: '/user/login', query: { redirect: to.fullPath } })
            })
          })
      } else {
        next()
      }
    }
  } else {
    if (whiteList.includes(to.name)) {
      // 在免登录白名单，直接进入
      next()
    } else {
      next({ path: '/user/login', query: { redirect: to.fullPath } })
      NProgress.done() // if current page is login will not trigger afterEach hook, so manually handle it
    }
  }
})

router.afterEach(() => {
  NProgress.done() // finish progress bar
})

/**
 * Action 权限指令
 * 指令用法：
 *  - 在需要控制 action 级别权限的组件上使用 v-action:[method] , 如下：
 *    <a-button v-action:add >添加用户</a-button>
 *    <a-button v-action:delete>删除用户</a-button>
 *    <a v-action:edit @click="edit(record)">修改</a>
 *  - jsx中请使用如下方式
 *    {this.$hasAction('add')?<a-button>添加用户</a-button>:''}
 *
 *  - 当前用户没有权限时，组件上使用了该指令则会被隐藏
 *  - 当后台权限跟 pro 提供的模式不同时，只需要针对这里的权限过滤进行修改即可
 *
 *  @see https://github.com/sendya/ant-design-pro-vue/pull/53
 */
function hasAction (actionName, permName) {
  if (!permName && this.$route) {
    permName = this.$route.meta.permissionId
  } 
  if (!permName) return

  const roles = store.getters.roles
  let actions = []
  roles.forEach(role => {
    role.permList.forEach(p => {
      if (p.permName !== permName) {
        return
      }
      actions.push(...p.actionList)
    })
  })
  return actions.indexOf(actionName) >= 0 
}

Vue.prototype.$hasAction = hasAction

const action = Vue.directive('action', {
  bind: function (el, binding, vnode) {
    const actionName = binding.arg
    const permissionId = vnode.context.$route.meta.permissionId
    
    if (!hasAction(actionName, permissionId)) {
      setTimeout(() => {
        if (el.parentNode == null) {
          el.style.display = 'none'
        } else {
          el.parentNode.removeChild(el)
        }
      }, 10)
    }
  }
})

export {
  action
}
