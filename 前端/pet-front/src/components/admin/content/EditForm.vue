<template>
  <div style="text-align: left">
    <el-button class="add-button" type="success" @click="dialogFormVisible = true">添加动物信息</el-button>
    <el-dialog
      title="添加/修改动物信息"
      :visible.sync="dialogFormVisible"
      @close="clear">
      <el-form v-model="form" style="text-align: left" ref="dataForm">
        <el-form-item label="动物昵称" :label-width="formLabelWidth" prop="name">
          <el-input v-model="form.name" autocomplete="off" placeholder="简单易记"></el-input>
        </el-form-item>
        <el-form-item label="品种" :label-width="formLabelWidth" prop="breed">
          <el-input v-model="form.breed" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="最近发现日期" :label-width="formLabelWidth" prop="date">
          <el-input v-model="form.date" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="年龄" :label-width="formLabelWidth" prop="age">
          <el-input v-model="form.age" autocomplete="off"></el-input>
        </el-form-item>
<!--        <el-form-item label="封面" :label-width="formLabelWidth" prop="pictyre">-->
<!--          <el-input v-model="form.ptcture" autocomplete="off" placeholder="图片 URL"></el-input>-->
<!--          <img-upload @onUpload="uploadImg" ref="imgUpload"></img-upload>-->
<!--        </el-form-item>-->
        <el-form-item label="地址" :label-width="formLabelWidth" prop="description">
          <el-input type="textarea" v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="简介" :label-width="formLabelWidth" prop="description">
          <el-input type="textarea" v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="id" style="height: 0">
          <el-input type="hidden" v-model="form.id" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="onSubmit">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import ImgUpload from './ImgUpload'
  export default {
    name: 'EditForm',
    components: {ImgUpload},
    data () {
      return {
        dialogFormVisible: false,
        form: {
          id: '',
          name: '',
          breed: '',
          date: '',
          age: '',
          address: '',
          picture: '',
          description: '',
          cid: ''
        },
        formLabelWidth: '120px'
      }
    },
    methods: {
      clear () {
        // this.$refs.imgUpload.clear()
        this.form = {
          id: '',
          name: '',
          breed: '',
          date: '',
          age: '',
          picture: '',
          address: '',
          status: '',
          description: '',
          cid: ''
        }
      },
      onSubmit () {
        this.$axios
          .post('/admin/content/animal', {
            id: this.form.id,
            picture: this.form.picture,
            name: this.form.name,
            breed: this.form.breed,
            date: this.form.date,
            age: this.form.age,
            address: this.from.address,
            description: this.form.description,
            area: this.form.area
          }).then(resp => {
            if (resp && resp.data.code === 200) {
              this.dialogFormVisible = false
              this.$emit('onSubmit')
            }
        })
      },
      uploadImg () {
        this.form.cover = this.$refs.imgUpload.url
      }
    }
  }
</script>

<style scoped>
  .add-button {
    margin: 18px 0 0 10px;
  }
</style>
