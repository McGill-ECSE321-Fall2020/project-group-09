import _ from 'lodash';
import axios from 'axios';
let config = require('../../../config');

let backendConfigurer = function () {
    switch (process.env.NODE_ENV) {
    case 'testing':
    case 'development':
        return 'http://' + config.dev.backendHost + ':' + config.dev.backendPort;
    case 'production':
        return 'https://' + config.build.backendHost + ':' + config.build.backendPort;
    }
}

let frontendConfigurer = function () {
    switch (process.env.NODE_ENV) {
    case 'testing':
    case 'development':
        return 'http://' + config.dev.host + ':' + config.dev.port;
    case 'production':
        return 'https://' + config.build.host + ':' + config.build.port;
    }
}

let backendUrl = backendConfigurer();
let frontendUrl = frontendConfigurer();

let AXIOS = axios.create({
    baseURL: backendUrl
    // headers: {'Access-Control-Allow-Origin': frontendUrl}
});

export default {
    name: 'Account',
    data() {
        return {
            userName: this.$route.params.userid,
            customerId: '',
            artistId: '',

            purchases: [],
            artPieces: [],
            addresses: [], // addresses displayed
            editAddressDialogVisible: false,
            addAddressDialogVisible: false,
            updatingAddress: { // address to be edited
                addressId: '',
                name: '',
                phoneNumber: '',
                streetAddress: '',
                city: '',
                province: '',
                postalCode: '',
                country: '',
                // REST parameters
                phone: '',
                streetaddress: '',
                postalcode: ''
            },
            newAddress: { // address to be added, formatted according to REST parameter names
                id: '',
                name: '',
                number: '',
                streetaddress: '',
                city: '',
                province: '',
                postcode: '',
                country: ''
            },
            errorCustomerId: '', // error retrieving customer id
            errorPurchases: '', // error retrieving purchases
            errorArtPieces: '', // error retrieving art pieces
            errorAddresses: '', // error retrieving addresses
            errorUpdateAddress: '', // error updating address
            errorNewAddress: '', // error creating address
            errorDeleteAddress: '', // error deleting address
            response: [],

            rules: {
                name: [ { required: true, message: "Name cannot be empty!", trigger: "blur" } ],
                phoneNumber: [ { required: true, message: "Phone number cannot be empty!", trigger: "blur" } ],
                streetAddress: [ { required: true, message: "Street address cannot be empty!", trigger: "blur" } ],
                city: [ { required: true, message: "City cannot be empty!", trigger: "blur" } ],
                province: [ { required: true, message: "Province cannot be empty!", trigger: "blur" } ],
                postalCode: [ { required: true, message: "Postal code cannot be empty!", trigger: "blur" } ],
                country: [ { required: true, message: "Country cannot be empty!", trigger: "blur" } ]
            }
        }
    },
    created: function () {

        AXIOS.get('/customer/user/'.concat(this.userName))
            .then(response => { this.customerId = response.data.userRoleId; })
            .catch(e => { this.errorCustomerId = e; console.log(e); });

        AXIOS.get('/purchasesbyuser/'.concat(this.userName))
            .then(response => { this.purchases = response.data; })
            .catch(e => { this.errorPurchases = e; console.log(e); });
        
        AXIOS.get('/artPiece/user/'.concat(this.userName))
            .then(response => { this.artPieces = response.data; })
            .catch(e => { this.errorArtPieces = e; console.log(e); });

        this.getAddresses();
    },

    methods: {

        getAddresses: function() { // retrieve or refresh
            AXIOS.get('/addresses/user/'.concat(this.userName))
                .then(response => { this.addresses = response.data; })
                .catch(e => { this.errorAddresses = e });
        },

        startUpdatingAddress: function (addressId) {
            AXIOS.get('/addresses/'.concat(addressId))
                .then(response => { this.updatingAddress = response.data; })
                .catch(e => { this.errorUpdateAddress = e; console.log(e); })
        },

        confirmUpdateAddress: function () {
            // Format REST parameter names
            this.updatingAddress.phone = this.updatingAddress.phoneNumber;
            this.updatingAddress.streetaddress = this.updatingAddress.streetAddress;
            this.updatingAddress.postalcode = this.updatingAddress.postalCode;

            AXIOS.put('/address/updatefull/'.concat(this.updatingAddress.addressId), {}, { params: this.updatingAddress })
                .then(_ => { 
                    this.editAddressDialogVisible = false;
                    this.getAddresses(); // refresh list of addresses
                })
                .catch(e => { this.errorUpdateAddress = e; console.log(e); })
        },

        handleDeleteAddress: function (addressId) {
            this.$confirm('Are you sure to delete this address?')
                .then(_ => { // Confirmed
                    AXIOS.put('/customer/deleteAddress/'.concat(this.customerId), {}, { params: { addressid: addressId } })
                    .then( _ => { this.getAddresses(); }) // refresh list of addresses
                    .catch(e => { errorDeleteAddress = e; console.log(e); })
                })
                .catch(_ => { /* Cancelled */ });
        },

        startCreatingAddress: function () {
            // reset new address
            this.newAddress.id = this.generateAddressId();
            this.newAddress.name = '';
            this.newAddress.number = '';
            this.newAddress.streetaddress = '';
            this.newAddress.city = '';
            this.newAddress.province = '';
            this.newAddress.postcode = '';
            this.newAddress.country = '';
        },

        confirmCreateAddress: function() {
            AXIOS.post('/address', {}, {params: this.newAddress})
                .then(_ => { 
                    AXIOS.put('/customer/addAddress/'.concat(this.customerId), {}, { params: { address: this.newAddress.id } } )
                        // add address in customer's address list
                        .then(_ => {
                            this.getAddresses(); // refresh address list
                            this.addAddressDialogVisible = false;
                        })
                        .catch(e => { this.errorNewAddress = e; console.log(e); });
                })
                .catch(e => { this.errorNewAddress = e; console.log(e); })
        },

        // Adapted from orderNumber in Purchase.js
        generateAddressId: function () {
            let now = Date.now().toString()
            now += now + Math.floor(Math.random() * 10)
            return  [now.slice(0, 4), now.slice(4, 10), now.slice(10, 14)].join('-')
        },

        handleClickArtPiece: function(artPieceId) {
            window.location.href = frontendUrl + '/#/home/' + this.userName + '/' + artPieceId;
        }

    }
}