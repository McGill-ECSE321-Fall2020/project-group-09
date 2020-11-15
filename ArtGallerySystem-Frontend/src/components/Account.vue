<template>
  <div id="Account">
    <h1> My Account </h1>

    <h2> Purchases </h2>
    <el-table :data = "purchases">
      <el-table-column prop = "orderId" label = "Order ID"></el-table-column>
      <el-table-column prop = "artPiece.artPieceId" label = "Art Piece Name">
        <template slot-scope="scope">
          <el-button
            @click="handleClickArtPiece(purchases[scope.$index].artPiece.artPieceId)" type="text" size="small"> 
            {{ purchases[scope.$index].artPiece.name }} 
          </el-button>
        </template>
      </el-table-column>
      <el-table-column prop = "date" label = "Purchase Date"></el-table-column>
      <el-table-column prop = "deliveryStatus" label = "Delivery Status"></el-table-column>
      <el-table-column type = "expand">
        <template slot-scope="scope">
          <p> <b>Delivery Method</b>: {{ purchases[scope.$index].deliveryMethod }} </p>
          <p> <b>Delivery Status</b>: {{ purchases[scope.$index].deliveryStatus }} </p>
          <span v-if="purchases[scope.$index].parcelDelivery">
            <p> <b>Carrier</b>: {{ purchases[scope.$index].delivery.carrier }} </p>
            <p> <b>Tracking Number</b>: {{ purchases[scope.$index].delivery.trackingNumber }} </p>
            <p> <b>Delivery Address</b>: </p>
            <p> {{ purchases[scope.$index].delivery.deliveryAddress.name }} </p>
            <p> {{ purchases[scope.$index].delivery.deliveryAddress.phoneNumber }} </p>
            <p> {{ purchases[scope.$index].delivery.deliveryAddress.streetAddress }} </p>
            <p> {{ purchases[scope.$index].delivery.deliveryAddress.city }} {{ purchases[scope.$index].delivery.deliveryAddress.province }} {{ purchases[scope.$index].delivery.deliveryAddress.postalCode }} {{ purchases[scope.$index].delivery.deliveryAddress.country }} </p>
          </span>
          <span v-if="purchases[scope.$index].inStorePickUp">
            <p> <b>Delivery ID / Pick-Up Reference Number</b>: {{ purchases[scope.$index].delivery.deliveryId }} </p>
            <p> <b>Store Address</b>: </p>
            <p> {{ artPieces[scope.$index].purchase.delivery.storeAddress.name }} </p>
            <p> {{ artPieces[scope.$index].purchase.delivery.storeAddress.phoneNumber }} </p>
            <p> {{ artPieces[scope.$index].purchase.delivery.storeAddress.streetAddress }} </p>
            <p> {{ artPieces[scope.$index].purchase.delivery.storeAddress.city }} {{ artPieces[scope.$index].purchase.delivery.storeAddress.province }} {{ artPieces[scope.$index].purchase.delivery.storeAddress.postalCode }} {{ artPieces[scope.$index].purchase.delivery.storeAddress.country }} </p>
          </span>
        </template>
      </el-table-column>
    </el-table>

    <h2> Uploaded Art Pieces </h2>
    <el-table :data = "artPieces">
      <el-table-column prop = "artPieceId" label = "Art Piece ID"></el-table-column>
      <el-table-column prop = "name" label = "Name">
        <template slot-scope="scope">
          <el-button @click="handleClickArtPiece(artPieces[scope.$index].artPieceId)" type="text" size="small"> {{ artPieces[scope.$index].name }} </el-button>
        </template>
      </el-table-column>
      <el-table-column prop = "date" label = "Upload Date"></el-table-column>
      <el-table-column prop = "artPieceStatus" label = "Status"></el-table-column>
      <el-table-column type = "expand">
        <template slot-scope="scope">
          <span v-if="artPieces[scope.$index].purchase">
            <p> <b>Order ID</b>: {{ artPieces[scope.$index].purchase.orderId }} </p>
            <p> <b>Order Date</b>: {{ artPieces[scope.$index].purchase.date }} </p>
            <p> <b>Delivery Method</b>: {{ artPieces[scope.$index].purchase.deliveryMethod }} </p>
            <p> <b>Delivery Status</b>: {{ artPieces[scope.$index].purchase.deliveryStatus }} </p>
            <span v-if="artPieces[scope.$index].purchase.parcelDelivery">
              <p> <b>Carrier</b>: {{ artPieces[scope.$index].purchase.delivery.carrier }} </p>
              <p> <b>Tracking Number</b>: {{ artPieces[scope.$index].purchase.delivery.trackingNumber }} </p>
              <p> <b>Delivery Address</b>: </p>
              <p> {{ artPieces[scope.$index].purchase.delivery.deliveryAddress.name }} </p>
              <p> {{ artPieces[scope.$index].purchase.delivery.deliveryAddress.phoneNumber }} </p>
              <p> {{ artPieces[scope.$index].purchase.delivery.deliveryAddress.streetAddress }} </p>
              <p> {{ artPieces[scope.$index].purchase.delivery.deliveryAddress.city }} {{ artPieces[scope.$index].purchase.delivery.deliveryAddress.province }} {{ artPieces[scope.$index].purchase.delivery.deliveryAddress.postalCode }} {{ artPieces[scope.$index].purchase.delivery.deliveryAddress.country }} </p>
              <el-button type = "text" 
                @click="startUpdatingParcelDeliveryInformation(artPieces[scope.$index].purchase.delivery.deliveryId)"> 
                Update Parcel Delivery Information </el-button>
              <el-dialog
                title="Update Parcel Delivery Information"
                :visible.sync="updateParcelDeliveryInformationDialogVisible"
                width="50%">
                <span>
                  <el-form ref="updateparceldelivery-form" :model="updatingParcelDelivery" label-width="130px">
                    <el-form-item label = "Status">
                      <el-select v-model="updatingParcelDelivery.parcelDeliveryStatus" placeholder="Select">
                        <el-option
                          v-for="item in [
                            {value: 'Pending', label: 'Pending'}, 
                            {value: 'Shipped', label: 'Shipped'},
                            {value: 'Delivered', label: 'Delivered'}
                          ]"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
                        </el-option>
                      </el-select>
                    </el-form-item>
                    <el-form-item label = "Carrier">
                      <el-input v-model = "updatingParcelDelivery.carrier"></el-input>
                    </el-form-item>
                    <el-form-item label = "Tracking Number">
                      <el-input v-model = "updatingParcelDelivery.trackingNumber"></el-input>
                    </el-form-item>
                  </el-form>
                </span>
                <span id="updateparceldelivery-error" v-if="errorParcelDelivery" style="color:red">Error: {{ errorParcelDelivery }}</span>
                <span slot="footer" class="dialog-footer">
                  <el-button @click="updateParcelDeliveryInformationDialogVisible = false">Cancel</el-button>
                  <el-button class = "custom" type="primary"
                    @click="confirmUpdateParcelDeliveryInformation()">
                    Confirm
                  </el-button>
                </span>
              </el-dialog>
            </span>
          </span>
          <span v-if="artPieces[scope.$index].purchase.inStorePickUp">
            <p> <b>Delivery ID / In-Store Pick-Up Reference Number</b>: {{ artPieces[scope.$index].purchase.delivery.deliveryId }} </p>
            <p> <b>Store Address</b>: </p>
              <p> {{ artPieces[scope.$index].purchase.delivery.storeAddress.name }} </p>
              <p> {{ artPieces[scope.$index].purchase.delivery.storeAddress.phoneNumber }} </p>
              <p> {{ artPieces[scope.$index].purchase.delivery.storeAddress.streetAddress }} </p>
              <p> {{ artPieces[scope.$index].purchase.delivery.storeAddress.city }} {{ artPieces[scope.$index].purchase.delivery.storeAddress.province }} {{ artPieces[scope.$index].purchase.delivery.storeAddress.postalCode }} {{ artPieces[scope.$index].purchase.delivery.storeAddress.country }} </p>
              
            <el-button type = "text" 
              @click="startUpdatingInStorePickUpInformation(artPieces[scope.$index].purchase.delivery.deliveryId)"> 
              Update In-Store Pick-Up Information </el-button>
            <el-dialog
              title="Update In-Store Pick-Up Information"
              :visible.sync="updateInStorePickUpInformationDialogVisible"
              width="50%">
              <span>
                <el-form ref="updateinstorepickup-form" :model="updatingInStorePickUp" label-width="120px">
                  <el-form-item label = "Status">
                    <el-select v-model="updatingInStorePickUp.inStorePickUpStatus" placeholder="Select">
                      <el-option
                        v-for="item in [
                          {value: 'Pending', label: 'Pending'}, 
                          {value: 'Available', label: 'Available for Pick-Up'},
                          {value: 'PickedUp', label: 'Picked Up'}
                        ]"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-form>
              </span>
              <span id="updateinstorepickup-error" v-if="errorInStorePickUp" style="color:red">Error: {{ errorInStorePickUp }}</span>
              <span slot="footer" class="dialog-footer">
                <el-button @click="updateInStorePickUpInformationDialogVisible = false">Cancel</el-button>
                <el-button class = "custom" type="primary"
                  @click="confirmUpdateInStorePickUpInformation()">
                  Confirm
                </el-button>
              </span>
            </el-dialog>
          </span>
        </template>
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
                <el-button class = "custom" type="primary"
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

    <el-button class = "custom" type="primary" @click="startCreatingAddress(); addAddressDialogVisible = true">Add Address</el-button>
    <el-button class = "custom" type="primary" @click="goBack">Back</el-button>
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
          <el-form-item label = "Phone Number" prop = "number">
            <el-input v-model = "newAddress.number"></el-input>
          </el-form-item>
          <el-form-item label = "Street Address" prop = "streetaddress">
            <el-input v-model = "newAddress.streetaddress"></el-input>
          </el-form-item>
          <el-form-item label = "City" prop = "city">
            <el-input v-model = "newAddress.city"></el-input>
          </el-form-item>
          <el-form-item label = "Province" prop = "province">
            <el-input v-model = "newAddress.province"></el-input>
          </el-form-item>
          <el-form-item label = "Postal Code" prop = "postcode">
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
        <el-button class = "custom" type="primary"
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
  .el-button.custom{
    /* color: white;
    background-color: #99ccff;
    border-color: #99ccff; */
    margin-top: 30px;
    margin-bottom: 70px;
  }

</style>
