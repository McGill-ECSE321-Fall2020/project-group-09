import axios from 'axios'
var config = require('../../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
  //header ('Access-Control-Allow-Origin: *')
})
function PurchaseDTO (orderId, date, orderStatus, delivery, payment, customer, artPiece){
  this.orderId = orderId;
  this.date = date;
  this.orderStatus = orderStatus;
  this.delivery = delivery;
  this.payment = payment;
  this.customer  = customer;
  this.artPiece = artPiece;
}
function PaymentDTO (paymentId, paymentMethod, isSuccessful, purchase){
  this.paymentId = paymentId;
  this.paymentMethod = paymentMethod;
  this.isSuccessful = isSuccessful;
  this.purchase = purchase;
}
function ArtPieceDTO (artPieceId, name, description, author, price, date,artPieceStatus, artist, purchase){
  this.artPieceId = artPieceId;
  this.name = name;
  this.description = description;
  this.author= author;
  this.date= date;
  this.artPieceStatus = artPieceStatus;
  this.price= price;
  this.artist= artist;
  this.purchase = purchase;
}
function InStorePickUpDTO (pickUpReferenceNumber, inStorePickUpStatus, storeAddress){
  this.pickUpReferenceNumber =pickUpReferenceNumber;
  this.inStorePickUpStatus = inStorePickUpStatus;
  this.storeAddress = storeAddress;
}
function ParcelDeliveryDTO (trackingNumber,carrier, parcelDeliveryStatus, deliveryAddress){
  this.trackingNumber =trackingNumber;
  this.carrier = carrier;
  this.parcelDeliveryStatus = parcelDeliveryStatus;
  this.deliveryAddress = deliveryAddress;
}
function AddressDTO (addressId, name, phoneNumber, streetAddress, city, province,postalCode, country){
  this.addressId = addressId;
  this.name = name;
  this.phoneNumber = phoneNumber;
  this.streetaddress= streetAddress;
  this.city= city;
  this.province = province;
  this.postalCode= postalCode;
  this.country = country;
}
function orderNumber() {
  let now = Date.now().toString() // '1492341545873'
  // pad with extra random digit
  now += now + Math.floor(Math.random() * 10)
  // format
  return  [now.slice(0, 4), now.slice(4, 10), now.slice(10, 14)].join('-')
}

export default {
  data() {
    return {
      dialogFormVisible: false,
      newadd : [],
      artpieceid: this.$route.params.artpieceid,
      userid : this.$route.params.userid,
      ordernum: '',
      purchases: [],
      payments: [],
      price: '',
      instorePickUps:[],
      parcelDeliverys:[],
      ruleForm1: {
        name: '',
        regionA: '',
        regionB:'',
        regionC:'',
      },
      ruleForm2: {
        descA: '',
        descB: '',
        descC: '',
        descD: '',
        descE: '',
        descF: '',
        descG: ''
      },
      rules: {
        name: [
          { required: true, message: 'Please input Card ID', trigger: 'blur' },
          { min: 16, max: 16, message: 'Length should be 16', trigger: 'blur' }
        ],
        regionA: [
          { required: true, message: 'Please select Payment Method', trigger: 'change' }
        ],
        descA: [
          { required: true, message: 'Please input country', trigger: 'blur' }
        ],
        descB: [
          { required: true, message: 'Please input city', trigger: 'blur' }
        ],
        descC: [
          { required: true, message: 'Please input postcode', trigger: 'blur' }
        ],
        descD: [
          { required: true, message: 'Please input province', trigger: 'blur' }
        ],
        descE: [
          { required: true, message: 'Please input street address', trigger: 'blur' }
        ],
        descF: [
          { required: true, message: 'Please input phone number', trigger: 'blur' }
        ],
        descG: [
          { required: true, message: 'Please input apt number', trigger: 'blur' }
        ]
      }
    };
  },
  created: function(){
    this.price = 0
    this.ordernum = orderNumber()
    AXIOS.get('/artPiece/getArtPiece/'.concat(this.artpieceid))
      .then(response => {
        if (!response.data || response.data.length <= 0) return;
        this.price= response.data.price;
      })
      .catch(e => {
        e = e.response.data.message ? e.response.data.message : e;
        console.log(e);
      });
    AXIOS.get('/customer/getCustomerAddress/'.concat(this.userid))
      .then(response => {
        this.parcelDeliverys = response.data;
        console.log(this.parcelDeliverys);
      })
      .catch(e => {
        e = e.response.data.message ? e.response.data.message : e;
        console.log(e);
      });

  },
  methods: {
    chooseInstore(){
      this.instorePickUps.push(this.ruleForm1.regionB)
      console.log(this.instorePickUps)
    },

    createPurchase(newPurchase) {
      AXIOS.post('/purchase/'.concat(this.ordernum.toString()), {}, {params: newPurchase})
        .then(response => {
          this.purchases.push(response.data)
          console.log(this.purchases)
          let pay = {
            id : Math.random().toString(36).substr(2, 9),
            success: "true",
            method: this.ruleForm1.regionA,
            purchaseid: this.ordernum,
            status: "Successful"
          }
          this.createPayment(pay);
        })
        .catch(e =>{
          console.log(e)
        })

    }  ,
    createAddress(address){
      AXIOS.post('/address',{}, {params: address})
        .then(response => {
          console.log(response.data)
          //his.newadd.push()
          this.newadd.push(response.data)
          console.log(this.newadd)

        })
        .catch(e =>{
          console.log(e)
        })
    },
    createPayment(payment) {
      AXIOS.post('/payment/', {}, {params: payment})
        .then(response => {
          this.payments.push(response.data)
          console.log(this.payments)
          if (this.ruleForm1.regionB == "storeA"){
            let idp = Math.random().toString(36).substr(2, 9)
            let store = {
              deliveryid: idp,
              pickUpReferenceNumber: idp,
              inStorePickUpStatus: "Available",
              storeAddress: "StoreA",
              purchaseid: this.ordernum
            }
            this.createInstorePickUp(store);
          }else if (this.ruleForm1.regionB == "storeB"){
            let idp = Math.random().toString(36).substr(2, 9)
            let store = {
              deliveryid: idp,
              pickUpReferenceNumber: idp,
              inStorePickUpStatus: "Available",
              storeAddress: "StoreB",
              purchaseid: this.ordernum
            }
            this.createInstorePickUp(store);
          }else if (this.ruleForm1.regionC){
            let idp = Math.random().toString(36).substr(2, 9)
            let parcel = {
              deliveryid: idp,
              trackingNumber: idp,
              carrier: "default",
              parcelDeliveryStatus: "Shipped",
              deliveryAddress: this.ruleForm1.regionC,
              purchaseid: this.ordernum
            }
            this.createParcelDelivery(parcel);
          }else if (this.newadd.length !== 0){
            console.log(this.newadd)
            let idp = Math.random().toString(36).substr(2, 9)
            let parcel = {
              deliveryid: idp,
              trackingNumber: idp,
              carrier: "default",
              parcelDeliveryStatus: "Shipped",
              deliveryAddress: this.newadd[0].addressId,
              purchaseid: this.ordernum
            }
            console.log(parcel)
            console.log(this.newadd[0])
            this.createParcelDelivery(parcel);
          }
          alert('Successful Payment');
          window.location.href='../';
        })
        .catch(e =>{
          console.log(e)
        })
    },
    createInstorePickUp(pickUp){
      AXIOS.post('/inStorePickUp', {}, {params: pickUp})
        .then(response => {
          this.instorePickUps.push(response.data)
        })
        .catch(e =>{
          console.log(e)
        })
    },
    createParcelDelivery(delivery){
      AXIOS.post('/parcelDelivery', {}, {params: delivery})
        .then(response => {
          this.parcelDeliverys.push(response.data)
          console.log(this.parcelDeliverys)
        })
        .catch(e =>{
          console.log(e)
        })
    },
    matchpayment (method){
      if (method== "Credit Card") return "Credit Card";
      else return "Debit Card";
    },

    submitForm(formName) {
      console.log(!this.ruleForm1.regionB)
      console.log(this.newadd.length==0)
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if ((!this.ruleForm1.regionB) && (!this.ruleForm1.regionC) && (this.newadd.length==0)){
            alert('Fill in address');
            return ;
          }
          if (( (this.ruleForm1.regionB) && (this.ruleForm1.regionC) )|| ( (this.ruleForm1.regionB) && ((this.newadd.length !==0)))||( (this.ruleForm1.regionC) && ((this.newadd.length !==0)))){
            alert('Fill in ONLY ONE address');
            return ;
          }
          console.log(this.ruleForm1.regionA);
          console.log(formName.toString());
          if (this.dialogFormVisible== true){
            this.dialogFormVisible =  false;
          }
          var today = new Date();
          var dd = today.getDate();
          var mm = today.getMonth()+1;
          var yyyy = today.getFullYear();
          if(dd<10)
          {
            dd='0'+dd;
          }
          if(mm<10)
          {
            mm='0'+mm;
          }
          today = yyyy+'-'+mm+'-'+dd;
          let pur = {
            customerid: this.userid,
            artpieceid: this.artpieceid,
            date : today,
            status: "Successful",
          }
          this.createPurchase(pur);
          /*let pay = {
            id : Math.random().toString(36).substr(2, 9),
            success: "true",
            method: this.ruleForm1.regionA,
            purchaseid: this.ordernum,
            status: "Successful"
          }
          this.createPayment(pay);
          if (this.ruleForm1.regionB == "storeA"){
            let store = {
              deliveryid: Math.random().toString(36).substr(2, 9),
              pickUpReferenceNumber: this.deliveryid,
              inStorePickUpStatus: "Available",
              storeAddress: "StoreA",
              purchaseid: this.ordernum
            }
            this.createInstorePickUp(store);
          }else if (this.ruleForm1.regionB == "storeB"){
            let store = {
              deliveryid: Math.random().toString(36).substr(2, 9),
              pickUpReferenceNumber: this.deliveryid,
              inStorePickUpStatus: "Available",
              storeAddress: "StoreB",
              purchaseid: this.ordernum
            }
            this.createInstorePickUp(store);
          }else if (this.ruleForm1.regionC != ""){
            let parcel = {
              deliveryid: Math.random().toString(36).substr(2, 9),
              trackingNumber: this.deliveryid,
              carrier: "default",
              parcelDeliveryStatus: "Shipped",
              deliveryAddress: this.ruleForm1.regionC.toString().split(" ")[0],
              purchaseid: this.ordernum
            }
            this.createParcelDelivery(parcel);
          }else if (this.newadd != []){
            let parcel = {
              deliveryid: Math.random().toString(36).substr(2, 9),
              trackingNumber: this.deliveryid,
              carrier: "default",
              parcelDeliveryStatus: "Shipped",
              deliveryAddress: this.newadd[0].addressId,
              purchaseid: this.ordernum
            }
            this.createParcelDelivery(parcel);
          }*/



        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    submitAddress(formName){
      this.$refs[formName].validate((valid) => {
      //this.newadd = true;
       if (valid) {
       let address = {
         id : Math.random().toString(36).substr(2, 9),
         country :this.ruleForm2.descA,
         city: this.ruleForm2.descB ,
         postcode: this.ruleForm2.descC,
         province:this.ruleForm2.descD,
         streetaddress:this.ruleForm2.descE,
         number:this.ruleForm2.descF,
         name:this.ruleForm2.descG
       }
       this.createAddress(address);
         if (this.dialogFormVisible== true){
           this.dialogFormVisible =  false;
         }
     }else {
       console.log('error submit!!');
       return false;
     }


    } )
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
};
