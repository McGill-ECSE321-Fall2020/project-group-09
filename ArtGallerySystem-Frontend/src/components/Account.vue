
<template>
  <div id="Account">
    <h1> Account Information </h1>

    <h2> Purchases </h2>
    <el-table :data = "purchases" :default-sort = "{prop: 'date', order: 'descending'}">
      <el-table-column prop = "orderId" label = "Order ID"></el-table-column>
      <el-table-column prop = "artPiece.artPieceId" label = "Art Piece Name">
        <template slot-scope="scope">
          <el-button @click="handleClickArtPiece(purchases[scope.$index].artPiece.artPieceId)" type="text" size="small"> {{ purchases[scope.$index].artPiece.name }} </el-button>
        </template>
      </el-table-column>
      <el-table-column prop = "date" label = "Purchase Date"></el-table-column>
      <el-table-column prop = "deliveryStatus" label = "Delivery Status"></el-table-column>
    </el-table>

    <h2> Uploaded Art Pieces </h2>
    <el-table :data = "artPieces" :default-sort = "{prop: 'date', order: 'descending'}">
      <el-table-column prop = "artPieceId" label = "Art Piece ID"></el-table-column>
      <el-table-column prop = "name" label = "Name">
        <template slot-scope="scope">
          <el-button @click="handleClickArtPiece(artPieces[scope.$index].artPieceId)" type="text" size="small"> {{ artPieces[scope.$index].name }} </el-button>
        </template>
      </el-table-column>
      <el-table-column prop = "date" label = "Upload Date"></el-table-column>
      <el-table-column prop = "artPieceStatus" label = "Status"></el-table-column>
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
                <el-form ref="updateaddress-form" :model="updatingAddress" label-width="120px" :rules = "rules">
                  <el-form-item label = "Address ID" prop = "addressId">
                    <el-input v-model = "updatingAddress.addressId" disabled></el-input>
                  </el-form-item>
                  <el-form-item label = "Name" prop = "name">
                    <el-input v-model = "updatingAddress.name"></el-input>
                  </el-form-item>
                  <el-form-item label = "Phone Number" prop = "phoneNumber">
                    <el-input v-model = "updatingAddress.phoneNumber"></el-input>
                  </el-form-item>
                  <el-form-item label = "Street Address" prop = "streetAddress">
                    <el-input v-model = "updatingAddress.streetAddress"></el-input>
                  </el-form-item>
                  <el-form-item label = "City" prop = "city">
                    <el-input v-model = "updatingAddress.city"></el-input>
                  </el-form-item>
                  <el-form-item label = "Province" prop = "province">
                    <el-input v-model = "updatingAddress.province"></el-input>
                  </el-form-item>
                  <el-form-item label = "Postal Code" prop = "postalCode">
                    <el-input v-model = "updatingAddress.postalCode"></el-input>
                  </el-form-item>
                  <el-form-item label = "Country" prop = "country">
                    <el-input v-model = "updatingAddress.country"></el-input>
                  </el-form-item>
                </el-form>
              </span>
              <span id="update-address-error" v-if="errorUpdateAddress" style="color:red">Error: {{ errorUpdateAddress }}</span>
              <span slot="footer" class="dialog-footer">
                <el-button @click="editAddressDialogVisible = false">Cancel</el-button>
                <el-button type="primary"
                  @click="confirmUpdateAddress()"
                  :disabled = !updatingAddress.name||!updatingAddress.phoneNumber||!updatingAddress.streetAddress||!updatingAddress.city||!updatingAddress.province||!updatingAddress.postalCode||!updatingAddress.country>
                  Confirm
                </el-button>
              </span>
            </el-dialog>

            <el-button type="text" @click="handleDeleteAddress(props.row.addressId)">Delete</el-button>

          </p>
        </template>
      </el-table-column>
    </el-table>

    <el-button type="primary" @click="startCreatingAddress(); addAddressDialogVisible = true">Add Address</el-button>
    <el-button type="primary" @click="goBack">Back</el-button>
    <el-dialog
      title="Add Address"
      :visible.sync="addAddressDialogVisible"
      width="50%">
      <span>
        <el-form ref="addaddress-form" :model="newAddress" label-width="120px" :rules = "rules">
          <el-form-item label = "Address ID" prop = "addressId">
            <el-input v-model = "newAddress.id" disabled></el-input>
          </el-form-item>
          <el-form-item label = "Name" prop = "name">
            <el-input v-model = "newAddress.name"></el-input>
          </el-form-item>
          <el-form-item label = "Phone Number" prop = "phoneNumber">
            <el-input v-model = "newAddress.number"></el-input>
          </el-form-item>
          <el-form-item label = "Street Address" prop = "streetAddress">
            <el-input v-model = "newAddress.streetaddress"></el-input>
          </el-form-item>
          <el-form-item label = "City" prop = "city">
            <el-input v-model = "newAddress.city"></el-input>
          </el-form-item>
          <el-form-item label = "Province" prop = "province">
            <el-input v-model = "newAddress.province"></el-input>
          </el-form-item>
          <el-form-item label = "Postal Code" prop = "postalCode">
            <el-input v-model = "newAddress.postcode"></el-input>
          </el-form-item>
          <el-form-item label = "Country" prop = "country">
            <el-input v-model = "newAddress.country"></el-input>
          </el-form-item>
        </el-form>
      </span>
      <span id="create-address-error" v-if="errorNewAddress" style="color:red">Error: {{ errorNewAddress }}</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addAddressDialogVisible = false">Cancel</el-button>
        <el-button type="primary"
          @click="confirmCreateAddress()"
          :disabled = !newAddress.name||!newAddress.number||!newAddress.streetaddress||!newAddress.city||!newAddress.province||!newAddress.postcode||!newAddress.country>
          Confirm
        </el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script src="./javascripts/Account.js"></script>

<style scoped>
  @import url("//unpkg.com/element-ui@2.14.0/lib/theme-chalk/index.css");
  .el-button{
    color: white;
    background-color: #99ccff;
    border-color: #99ccff;
    margin-top: 30px;
    margin-bottom: 70px;
  }

</style>
