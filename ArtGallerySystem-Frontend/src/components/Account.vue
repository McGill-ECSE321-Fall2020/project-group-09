
<template>
  <div id="Account">
    <h1> Account Information </h1>

    <h2> Purchases </h2>
    <el-table :data = "purchases">
      <el-table-column prop = "orderId" label = "Order ID"></el-table-column>
      <el-table-column prop = "artPiece.artPieceId" label = "Art Piece"></el-table-column>
      <el-table-column prop = "date" label = "Date"></el-table-column>
      <el-table-column prop = "deliveryStatus" label = "Delivery Status"></el-table-column>
      <el-table-column label = "Link">
        <el-button @click="handleClickArtPiece(artPiece.artPieceId)" type="text" size="small"> Art Piece Detail Page </el-button>
      </el-table-column>
    </el-table>

    <h2> Uploaded Art Pieces </h2>
    <el-table :data = "artPieces">
      <el-table-column prop = "artPieceId" label = "Art Piece ID"></el-table-column>
      <el-table-column prop = "name" label = "Name"></el-table-column>
      <el-table-column prop = "date" label = "Date"></el-table-column>
      <el-table-column prop = "artPieceStatus" label = "Status"></el-table-column>
      <el-table-column label = "Link">
        <el-button @click="handleClickArtPiece(artPieceId)" type="text" size="small"> Art Piece Detail Page </el-button>
      </el-table-column>
    </el-table>

    <h2> Addresses </h2>
    <el-table :data = "addresses">
      <el-table-column prop = "addressId" label = "Address ID"></el-table-column>
      <el-table-column type="expand">
        <template slot-scope="props">
          <p>{{ props.row.name }}</p>
          <p>{{ props.row.phoneNumber }}</p>
          <p>{{ props.row.streetAddress }}</p>
          <p>{{ props.row.city }} {{ props.row.province }} {{ props.row.postalCode }} {{ props.row.country }}</p>
          <p>
            <el-button type="text" @click="startUpdatingAddress(props.row.addressId); editAddressDialogVisible = true">Edit</el-button>
            <el-dialog
              title="Edit Address"
              :visible.sync="editAddressDialogVisible"
              width="50%">
              <span>
                <el-form ref="updateaddress-form" :model="updatingAddress" label-width="120px">
                  <el-form-item label = "Address ID">
                    <el-input v-model = "updatingAddress.addressId" disabled></el-input>
                  </el-form-item>
                  <el-form-item label = "Name">
                    <el-input v-model = "updatingAddress.name"></el-input>
                  </el-form-item>
                  <el-form-item label = "Phone Number">
                    <el-input v-model = "updatingAddress.phoneNumber"></el-input>
                  </el-form-item>
                  <el-form-item label = "Street Address">
                    <el-input v-model = "updatingAddress.streetAddress"></el-input>
                  </el-form-item>
                  <el-form-item label = "City">
                    <el-input v-model = "updatingAddress.city"></el-input>
                  </el-form-item>
                  <el-form-item label = "Province">
                    <el-input v-model = "updatingAddress.province"></el-input>
                  </el-form-item>
                  <el-form-item label = "Postal Code">
                    <el-input v-model = "updatingAddress.postalCode"></el-input>
                  </el-form-item>
                  <el-form-item label = "Country">
                    <el-input v-model = "updatingAddress.country"></el-input>
                  </el-form-item>
                </el-form>
              </span>
              <span slot="footer" class="dialog-footer">
                <el-button @click="editAddressDialogVisible = false">Cancel</el-button>
                <el-button type="primary" @click="confirmUpdateAddress()">Confirm</el-button>
              </span>
            </el-dialog>

            <el-button type="text" @click="handleDeleteAddress(props.row.addressId)">Delete</el-button>
            
          </p>
        </template>
      </el-table-column>
    </el-table>

    <el-button type="primary" @click="startCreatingAddress(); addAddressDialogVisible = true">Add Address</el-button>
    <el-dialog
      title="Add Address"
      :visible.sync="addAddressDialogVisible"
      width="50%">
      <span>
        <el-form ref="addaddress-form" :model="newAddress" label-width="120px">
          <el-form-item label = "Address ID">
            <el-input v-model = "newAddress.id" disabled></el-input>
          </el-form-item>
          <el-form-item label = "Name">
            <el-input v-model = "newAddress.name"></el-input>
          </el-form-item>
          <el-form-item label = "Phone Number">
            <el-input v-model = "newAddress.number"></el-input>
          </el-form-item>
          <el-form-item label = "Street Address">
            <el-input v-model = "newAddress.streetaddress"></el-input>
          </el-form-item>
          <el-form-item label = "City">
            <el-input v-model = "newAddress.city"></el-input>
          </el-form-item>
          <el-form-item label = "Province">
            <el-input v-model = "newAddress.province"></el-input>
          </el-form-item>
          <el-form-item label = "Postal Code">
            <el-input v-model = "newAddress.postcode"></el-input>
          </el-form-item>
          <el-form-item label = "Country">
            <el-input v-model = "newAddress.country"></el-input>
          </el-form-item>
        </el-form>
      </span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addAddressDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="confirmCreateAddress()">Confirm</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script src="./javascripts/Account.js"></script>

<style scoped>
  @import url("//unpkg.com/element-ui@2.14.0/lib/theme-chalk/index.css");
</style>