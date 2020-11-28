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

            updateParcelDeliveryInformationDialogVisible: false,
            updatingParcelDelivery: { // data of parcel delivery to be updated
                deliveryId: '',
                parcelDeliveryStatus: '',
                carrier: '',
                trackingNumber: ''
            },

            updateInStorePickUpInformationDialogVisible: false,
            updatingInStorePickUp: { // data of in-store pick-up to be updated
                deliveryId: '',
                inStorePickUpStatus: '',
                pickUpReferenceNumber: ''
            },

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
            errorParcelDelivery: '', // error updating parcel delivery
            errorInStorePickUp: '', // error updating in-store pick-up
            errorAddresses: '', // error retrieving addresses
            errorUpdateAddress: '', // error updating address
            errorNewAddress: '', // error creating address
            errorDeleteAddress: '', // error deleting address
            response: [],

            rules: { // for address entries
                name: [ { required: true, message: "Name cannot be empty!", trigger: "blur" } ],
                phoneNumber: [ { required: true, message: "Phone number cannot be empty!", trigger: "blur" } ],
                number: [ { required: true, message: "Phone number cannot be empty!", trigger: "blur" } ],
                streetAddress: [ { required: true, message: "Street address cannot be empty!", trigger: "blur" } ],
                streetaddress: [ { required: true, message: "Street address cannot be empty!", trigger: "blur" } ],
                city: [ { required: true, message: "City cannot be empty!", trigger: "blur" } ],
                province: [ { required: true, message: "Province cannot be empty!", trigger: "blur" } ],
                postalCode: [ { required: true, message: "Postal code cannot be empty!", trigger: "blur" } ],
                postcode: [ { required: true, message: "Postal code cannot be empty!", trigger: "blur" } ],
                country: [ { required: true, message: "Country cannot be empty!", trigger: "blur" } ]
            }
        }
    },

    // Get customer id, purchases, art pieces, and addresses
    created: function () {

        AXIOS.get('/customer/user/'.concat(this.userName))
            .then(response => { this.customerId = response.data.userRoleId; })
            .catch(e => { this.errorCustomerId = e; console.log(e); });

        AXIOS.get('/purchasesbyuser/'.concat(this.userName))
            .then(response => { this.purchases = response.data })
            .catch(e => { this.errorPurchases = e; console.log(e); });
            
        this.getArtPieces();

        this.getAddresses();
    },

    methods: {

        // Retrieve or refresh addresses
        getAddresses: function() {
            AXIOS.get('/addresses/user/'.concat(this.userName))
                .then(response => { this.addresses = response.data; })
                .catch(e => { this.errorAddresses = e });
        },

        // Retreive or refresh art pieces
        getArtPieces: function() {
            AXIOS.get('/artPiece/userrole/'.concat(this.userName).concat("--Artist"))
                .then(response => { this.artPieces = response.data })
                .catch(e => { this.errorArtPieces = e; console.log(e); });
        },

        // Back to home page
        goBack(){
            window.location.href = frontendUrl + '/#/home/' + this.userName;
        },

        // Show dialog, retrieve current information
        startUpdatingParcelDeliveryInformation: function (deliveryId) {
            AXIOS.get('/parcelDeliveryes/'.concat(deliveryId))
                .then(response => {
                    this.updateParcelDeliveryInformationDialogVisible = true
                    this.updatingParcelDelivery = response.data; 
                })
                .catch(e => { this.errorParcelDelivery = e; console.log(e) })
        },

        // Call REST controller, refresh list, notify success
        confirmUpdateParcelDeliveryInformation: function() {
            AXIOS.put('/parcelDelivery/updateFull/'.concat(this.updatingParcelDelivery.deliveryId), {}, 
                {params: this.updatingParcelDelivery})
                .then(_ => {
                    this.updateParcelDeliveryInformationDialogVisible = false
                    this.getArtPieces() // refresh
                    this.$notify({
                        title: 'Success',
                        message: 'Parcel delivery information updated successfully!',
                        type: 'success'
                    })
                })
                .catch(e => { this.errorParcelDelivery = e; console.log(e) })
        },

        // Show dialog, retrieve current status
        startUpdatingInStorePickUpInformation: function (deliveryId) {
            AXIOS.get('/inStorePickUps/'.concat(deliveryId))
                .then(response => {
                    this.updateInStorePickUpInformationDialogVisible = true
                    this.updatingInStorePickUp = response.data
                })
                .catch(e => { this.errorInStorePickUp = e; console.log(e) })
            this.updatingInStorePickUp.pickUpReferenceNumber = deliveryId
        },

        // Update in-store pick-up status via REST controller, refresh list, notify success
        confirmUpdateInStorePickUpInformation: function () {
            AXIOS.put('/inStorePickUp/update/'.concat(this.updatingInStorePickUp.deliveryId), {}, 
                {params: {inStorePickUp: this.updatingInStorePickUp.inStorePickUpStatus}})
                .then(_ => {
                    this.updateInStorePickUpInformationDialogVisible = false
                    this.getArtPieces() // refresh
                    this.$notify({
                        title: 'Success',
                        message: 'In-store pick-up information updated successfully!',
                        type: 'success'
                    })
                })
                .catch(e => { this.errorInStorePickUp = e; console.log(e) })
        },

        // Retrieve address and fill in entries
        startUpdatingAddress: function (addressId) {
            AXIOS.get('/addresses/'.concat(addressId))
                .then(response => { this.updatingAddress = response.data; })
                .catch(e => { this.errorUpdateAddress = e; console.log(e); })
        },

        // Update address via REST controller, refresh list, notify success
        confirmUpdateAddress: function () {
            // Format REST parameter names
            this.updatingAddress.phone = this.updatingAddress.phoneNumber;
            this.updatingAddress.streetaddress = this.updatingAddress.streetAddress;
            this.updatingAddress.postalcode = this.updatingAddress.postalCode;

            AXIOS.put('/address/updatefull/'.concat(this.updatingAddress.addressId), {}, { params: this.updatingAddress })
                .then(_ => {
                    this.editAddressDialogVisible = false;
                    this.getAddresses() // refresh list of addresses
                    this.$notify({
                        title: 'Success',
                        message: 'Address updated successfully!',
                        type: 'success'
                    })
                })
                .catch(e => { this.errorUpdateAddress = e; console.log(e); })
        },

        // Confirm dialog, delete address from customer's address list, notify success
        // (not completely deleted in database as it may still be referenced by some delivery!)
        handleDeleteAddress: function (addressId) {
            this.$confirm('Are you sure to delete this address?')
                .then(_ => { // Confirmed
                    AXIOS.put('/customer/deleteAddress/'.concat(this.customerId), {}, { params: { addressid: addressId } })
                    .then( _ => {
                        this.getAddresses() // refresh list of addresses
                        this.$notify({
                            title: 'Success',
                            message: 'Address deleted successfully!',
                            type: 'success'
                        })
                    }) 
                    .catch(e => { errorDeleteAddress = e; console.log(e); })
                })
                .catch(_ => { /* Cancelled */ });
        },

        // Clear entries, generate id for new address
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

        // Post new address via REST controller, refresh list, notify success
        confirmCreateAddress: function() {
            AXIOS.post('/address', {}, {params: this.newAddress})
                .then(_ => {
                    AXIOS.put('/customer/addAddress/'.concat(this.customerId), {}, //!!!
                        { params: { address: this.newAddress.id } } )
                        // add address in customer's address list
                        .then(_ => {
                            this.addAddressDialogVisible = false;
                            this.getAddresses() // refresh list of addresses
                            this.$notify({
                                title: 'Success',
                                message: 'Address created successfully!',
                                type: 'success'
                            })
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

        // To art piece description page
        handleClickArtPiece: function(artPieceId) {
            window.location.href = frontendUrl + '/#/home/' + this.userName + '/' + artPieceId;
        }

    }
}
