new Vue({
    el: '#menu',

    ready: function ()
    {
        var self = this
        window.addEventListener('click', function (e) {
            if (!e.target.parentNode.classList.contains('menu__link--toggle'))
            {
                self.close()
            }
        }, false)
    },

    data: {
        dropDowns: {
            info: {open: false},
            prob: {open: false}
        }
    },

    methods: {
        toggle: function (dropdownName)
        {

            this.dropDowns[dropdownName].open = !this.dropDowns[dropdownName].open;
        },

        close: function ()
        {
            for (dd in this.dropDowns)
            {
                this.dropDowns[dd].open = false;
            }
        }
    }

})
//
Vue.use(VueForm);

new Vue({
    el: '#app',
    data: {
        formstate: {},
        feedback: {},
        model: {
            intro: '',
            cause: ''
        }
    },
    methods: {
        onSubmit: function () {
            if (this.formstate.$invalid) {
                // alert user and exit early
                return;
            } else {
                {
                    // GET /someUrl
                    Vue.http.post('/api/1.0/cheers', this.model).then(response => {

                        // get body data
                        this.feedback = response.body;
                        cheer.gridData.push(this.feedback);
                    }, response => {
                        // error callback
                    });
                }
            }
            // otherwise submit form
        }
    }
});

//
http://127.0.0.1:8080/api/1.0/cheers    

        // register the grid component
        Vue.component('cheer-grid', {
            template: '#cheer-template',
            props: {
                data: Array,
                columns: Array,
                filterKey: String
            },
            data: function () {
                var sortOrders = {}
                this.columns.forEach(function (key) {
                    sortOrders[key] = 1
                })
                return {
                    sortKey: '',
                    sortOrders: sortOrders
                }
            },
            computed: {
                filteredData: function () {
                    var sortKey = this.sortKey
                    var filterKey = this.filterKey && this.filterKey.toLowerCase()
                    var order = this.sortOrders[sortKey] || 1
                    var data = this.data
                    if (filterKey) {
                        data = data.filter(function (row) {
                            return Object.keys(row).some(function (key) {
                                return String(row[key]).toLowerCase().indexOf(filterKey) > -1
                            })
                        })
                    }
                    if (sortKey) {
                        data = data.slice().sort(function (a, b) {
                            a = a[sortKey]
                            b = b[sortKey]
                            return (a === b ? 0 : a > b ? 1 : -1) * order
                        })
                    }
                    return data
                }
            },
            filters: {
                capitalize: function (str) {
                    return str.charAt(0).toUpperCase() + str.slice(1)
                }
            },
            methods: {
                sortBy: function (key) {
                    this.sortKey = key
                    this.sortOrders[key] = this.sortOrders[key] * -1
                }
            }
        })

var cheer = new Vue({
    el: '#cheer',
    data: {
        searchQuery: '',
        gridColumns: ['intro', 'cause'],
        gridData: [
            {name: '..', cause: ''}
        ]
    }
})

Vue.http.get('/api/1.0/cheers/').then(response => {

    // get body data               
    cheer.gridColumns = ['intro', 'cause'],
    cheer.gridData = response.body;

}, response => {
    // error callback
});

//
//
http://127.0.0.1:8080/api/1.0/cheers/5    

        // register the grid component
        Vue.component('env-grid', {
            template: '#env-template',
            props: {
                data: Array,
                columns: Array,
                filterKey: String
            },
            data: function () {
                var sortOrders = {}
                this.columns.forEach(function (key) {
                    sortOrders[key] = 1
                })
                return {
                    sortKey: '',
                    sortOrders: sortOrders
                }
            },
            computed: {
                filteredData: function () {
                    var sortKey = this.sortKey
                    var filterKey = this.filterKey && this.filterKey.toLowerCase()
                    var order = this.sortOrders[sortKey] || 1
                    var data = this.data
                    if (filterKey) {
                        data = data.filter(function (row) {
                            return Object.keys(row).some(function (key) {
                                return String(row[key]).toLowerCase().indexOf(filterKey) > -1
                            })
                        })
                    }
                    if (sortKey) {
                        data = data.slice().sort(function (a, b) {
                            a = a[sortKey]
                            b = b[sortKey]
                            return (a === b ? 0 : a > b ? 1 : -1) * order
                        })
                    }
                    return data
                }
            },
            filters: {
                capitalize: function (str) {
                    return str.charAt(0).toUpperCase() + str.slice(1)
                }
            },
            methods: {
                sortBy: function (key) {
                    this.sortKey = key
                    this.sortOrders[key] = this.sortOrders[key] * -1
                }
            }
        })

var env = new Vue({
    el: '#env',
    data: {
        searchQuery: '',
        gridColumns: ['name', 'value'],
        gridData: [
            {name: '', value: ''}

        ]
    }
})

Vue.http.get('/api/1.0/infos/env').then(response => {
    // get body data
    env.gridColumns = ['name', 'value'],
    env.gridData = response.body;

}, response => {
    // error callback
});

// dns

new Vue({
    el: '#fqdnquery',
    data: {
        formstate: {},
        fqdnResp: {},
        model: {

            host: '',
            ip: '',
            isReachable: '',
            isAnyLocalAddress: '',            
            isMulticastAddress: ''
        }
    },
    methods: {
        onSubmit: function () {
            if (this.formstate.$invalid) {
                // alert user and exit early
                return;
            } else {
                {
                    // GET /someUrl
                    Vue.http.post('/api/1.0/info/dns', this.model).then(response => {

                        // get body data
                        this.fqdnResp = response.body;
                        
                    }, response => {
                        // error callback
                    });
                }
            }
            // otherwise submit form
        }
    }
});

var fqdn = new Vue({
    el: '#fqdn',
    data: {
        searchQuery: '',
        gridColumns: ['name', 'value'],
        gridData: [
            {name: '', value: ''}

        ]
    }
});
        Vue.component('fqdn-grid', {
            template: '#grid-template',
            props: {
                data: Array,
                columns: Array,
                filterKey: String
            },
            data: function () {
                var sortOrders = {}
                this.columns.forEach(function (key) {
                    sortOrders[key] = 1
                })
                return {
                    sortKey: '',
                    sortOrders: sortOrders
                }
            },
            computed: {
                filteredData: function () {
                    var sortKey = this.sortKey
                    var filterKey = this.filterKey && this.filterKey.toLowerCase()
                    var order = this.sortOrders[sortKey] || 1
                    var data = this.data
                    if (filterKey) {
                        data = data.filter(function (row) {
                            return Object.keys(row).some(function (key) {
                                return String(row[key]).toLowerCase().indexOf(filterKey) > -1
                            })
                        })
                    }
                    if (sortKey) {
                        data = data.slice().sort(function (a, b) {
                            a = a[sortKey]
                            b = b[sortKey]
                            return (a === b ? 0 : a > b ? 1 : -1) * order
                        })
                    }
                    return data
                }
            },
            filters: {
                capitalize: function (str) {
                    return str.charAt(0).toUpperCase() + str.slice(1)
                }
            },
            methods: {
                sortBy: function (key) {
                    this.sortKey = key
                    this.sortOrders[key] = this.sortOrders[key] * -1
                }
            }
        });