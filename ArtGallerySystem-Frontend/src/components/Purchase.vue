<template>
  <div>
  <el-row>
      <div align = "center">
        <p v-bind:value="userid">User ID : {{userid}}</p>
        <p v-bind:value="artpieceid">Artpiece ID : {{artpieceid}}</p>
        <p v-bind:value="ordernum">Order Number : {{ordernum}}</p>
        <p v-bind:value="price">Price : {{price}}</p>
      </div>
  </el-row>
  <el-row>
    <el-row >
      <div align = "center">
        <el-form :model="ruleForm1" :rules="rules" ref="ruleForm1" class="demo-ruleForm" label-width="300px" style = "width: 600px">

          <el-form-item class= "form1" label="Payment Method" prop="regionA">
            <el-select v-model="ruleForm1.regionA" placeholder="Choose One Payment Method" style="width:300px">
              <el-option label="CreditCard" value="CreditCard">CreditCard</el-option>
              <el-option label="DebitCard" value="DebitCard">DebitCard</el-option>
            </el-select>
          </el-form-item>

          <el-form-item class= "form1" label="Card Number" prop="name">
            <el-input v-model="ruleForm1.name" style = "width: 300px"></el-input>
          </el-form-item>
          <el-form-item label = "Delivery Method:"> </el-form-item>
          <el-form-item class= "form1" label="In-Store Pick-Up" prop="regionB">
            <el-select v-model="ruleForm1.regionB" clearable placeholder="Choose One Store" @click="chooseInstore" style="width:300px">
              <el-option label="StoreA" value="storeA">StoreA</el-option>
              <el-option label="StoreB" value="storeB">StoreB</el-option>
            </el-select>
          </el-form-item>
          <el-form-item label = "Or"> </el-form-item>
          <el-form-item class= "form1" label="Parcel Delivery to a Saved Address" prop="regionC">
            <el-select v-model="ruleForm1.regionC" clearable placeholder="Choose One Address" align="left" style="width:300px">
              <el-option v-for= "(address, i) in parcelDeliverys" :key="`address-${i}`" :label= "address.addressId" :value="address.addressId">{{address.addressId +" "+ address.streetAddress}}</el-option>
            </el-select>
          </el-form-item>
          <el-form-item label = "Or"> 
            <el-button type="text" @click="dialogFormVisible = true">Parcel Delivery to a New Address</el-button> 
          </el-form-item>
          <el-dialog title="Shipping address" :visible.sync="dialogFormVisible">
            <el-form :model="ruleForm2" :rules="rules" ref="ruleForm2">
              <el-form-item label="Country" prop="descA">
                <el-input type="textarea" v-model="ruleForm2.descA"></el-input>
              </el-form-item>
              <el-form-item label="City" prop="descB">
                <el-input type="textarea" v-model="ruleForm2.descB"></el-input>
              </el-form-item>
              <el-form-item label="PostCode" prop="descC">
                <el-input type="textarea" v-model="ruleForm2.descC"></el-input>
              </el-form-item>
              <el-form-item label="Province" prop="descD">
                <el-input type="textarea" v-model="ruleForm2.descD"></el-input>
              </el-form-item>
              <el-form-item label="StreetAddress" prop="descE">
                <el-input type="textarea" v-model="ruleForm2.descE"></el-input>
              </el-form-item>
              <el-form-item label="PhoneNumber" prop="descF">
                <el-input type="textarea" v-model="ruleForm2.descF"></el-input>
              </el-form-item>
              <el-form-item label="Name" prop="descG">
                <el-input type="textarea" v-model="ruleForm2.descG"></el-input>
              </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">Cancel</el-button>
              <el-button type="primary" @click="submitAddress('ruleForm2')">Confirm</el-button>
            </span>
          </el-dialog>
          <el-button type="primary" @click="submitForm('ruleForm1')">Purchase</el-button>
          <el-button @click="resetForm('ruleForm1')">Reset</el-button>
          <el-button @click="goBack">Back</el-button>
        </el-form>
      </div>
    </el-row>

  </el-row>
  </div>
</template>

<script src="./javascripts/Purchase.js">
</script>
<style scoped>
  @import url("//unpkg.com/element-ui@2.14.0/lib/theme-chalk/index.css");
  /*.el-form-item.form1 {
    align-items: center;
    align-content: center;
    text-align: center; 
    margin-left: 500px;
  }
  .el-form-item [class="demo-ruleForm"]{
    align-content: left
    margin-left: 500px;
  }
  .div .el-row .div .p {
    margin-left: 500px;
  }*/
</style>
