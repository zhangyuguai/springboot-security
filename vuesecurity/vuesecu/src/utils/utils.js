import {getRequest} from "@/utils/api";

export const initMenu = (router, store) => {
    if (store.state.routes.length > 0) {
        return;
    }

    var user = store.state.user;
    if (user!==null){
        getRequest(`http://localhost:8081/config/initMenu/${user.userId}`).then(
            resp => {
                if (resp && resp.status === 200) {
                    var fmtRoutes = formatRoutes(resp.data.data);
                    router.addRoutes(fmtRoutes);
                    store.commit('initMenu', fmtRoutes);
                }
            }
        )
    }

}
export const formatRoutes = (routes) => {
    let fmRoutes = [];
    routes.forEach(router => {
        let {
            path,
            component,
            menuName,
            meta,
            icon,
            children
        } = router;
        if (children && children instanceof Array) {
            children = formatRoutes(children);
        }
        let fmRouter = {
            path: path,
            component(resolve) {
                if (component.startsWith("Home")) {
                    require(['../components/' + component + '.vue'], resolve)
                } else if (component.startsWith("Emp")) {
                    require(['../components/emp/' + component + '.vue'], resolve)
                } else if (component.startsWith("Per")) {
                    require(['../components/personnel/' + component + '.vue'], resolve)
                } else if (component.startsWith("Sal")) {
                    require(['../components/salary/' + component + '.vue'], resolve)
                } else if (component.startsWith("Sta")) {
                    require(['../components/statistics/' + component + '.vue'], resolve)
                } else if (component.startsWith("Sys")) {
                    require(['../components/system/' + component + '.vue'], resolve)
                }
            },
            menuName: menuName,
            icon: icon,
            meta: meta,
            children: children
        };
        fmRoutes.push(fmRouter);
    })
    return fmRoutes;
}