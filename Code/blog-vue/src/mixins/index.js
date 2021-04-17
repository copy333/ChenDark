export const mixin = {
    methods: {
        //提示信息，Notification 通知
        notify(title, type) {
            this.$notify({
                title: title,
                type: type
            })
        },
    }
}