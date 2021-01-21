<template>
    <div>
      <el-form>
        <el-form-item v-for="a in  data" :key="a.id" :label="a.nameCH">
            <!--  0 下拉框     1 单选框      2  复选框   3  输入框  -->
            <!--  skuCK0color    skuCK1szie   -->
            <el-checkbox-group  v-model="a.ckValues" @change="skuChange">
              <el-checkbox v-for="b in a.value" :key="b.id" :label="b.valueCH"></el-checkbox>
            </el-checkbox-group>
        </el-form-item>
      </el-form>

      <el-table
        v-if="tableShow"
        :data="tableData"
        style="width: 100%">

        <el-table-column v-for="c in cols" :key="c.id" :label="c.nameCH" :prop="c.name">
        </el-table-column>

        <el-table-column
          label="库存"
          width="180">

          <template slot-scope="scope">
            <el-input/>
          </template>

        </el-table-column>
        <el-table-column
          label="价格"
          width="180">
          <template slot-scope="scope">
            <el-input/>
          </template>
        </el-table-column>
      </el-table>

    </div>
</template>

<script>
    export default {
        name: "Test",
        data(){
            return{
                  data:[
                    {"id":1,"name":"color",nameCH:"颜色",ckValues:[],
                      value:[
                        {id:1,"value":"red","valueCH":"红色"},
                        {id:1,"value":"green","valueCH":"绿色"},
                        {id:1,"value":"blue","valueCH":"蓝色"}
                      ]
                    },

                    {"id":2,"name":"size",nameCH:"大小",ckValues:[],
                      value:[
                        {id:1,"value":"xl","valueCH":"大号"},
                        {id:1,"value":"s","valueCH":"小号"},
                        {id:1,"value":"m","valueCH":"中号"}
                      ]
                    },
                    {"id":3,"name":"system",nameCH:"系统",ckValues:[],
                      value:[
                        {id:1,"value":"ios","valueCH":"苹果系统"},
                        {id:1,"value":"andri","valueCH":"安卓系统"},
                        {id:1,"value":"window","valueCH":"微软系统"}
                      ]
                    },
                    {"id":3,"name":"sssiz",nameCH:"尺寸",ckValues:[],
                      value:[
                        {id:1,"value":"x","valueCH":"x"},
                        {id:1,"value":"l","valueCH":"l"},
                        {id:1,"value":"m","valueCH":"m"}
                      ]
                    }
                  ],
              tableShow:false,//是否显示table
              cols:[],//表动态列头
              tableData:[]
            }
        },
      methods:{
        //监听sku属性 改变事件
        skuChange:function(){
          //清空动态列头
          this.cols=[];
          this.tableData=[];

          //声明笛卡尔积的参数
          let dikaParams=[];
          //判断是否要生成笛卡尔积
          let flag=true;
          debugger;
          for (let i = 0; i <this.data.length ; i++) {
            //添加动态列头名称
            this.cols.push({"id":this.data[i].id,"nameCH":this.data[i].nameCH,"name":this.data[i].name});
            //添加笛卡尔积参数
            dikaParams.push(this.data[i].ckValues);
            //判断当前sku属性 是否被选中
            if(this.data[i].ckValues.length==0){
              flag=false;
              break;
            }
          }
          if(flag==true){
            console.log(dikaParams);
            alert("生成笛卡尔积");
           let res=this.calcDescartes(dikaParams);
           //遍历结果集   ["","",""]
            for (let i = 0; i <res.length ; i++) {
              debugger;
              //得到数据
             let valuesAttr=res[i];
             let  tableValue={};
                if(typeof valuesAttr=="object"){
              for (let j = 0; j < valuesAttr.length; j++) {
                let key=this.cols[j].name;
                tableValue[key]=valuesAttr[j];
              }
                }else{
                    let key=this.cols[0].name;
                    tableValue[key]=valuesAttr;
                }
              this.tableData.push(tableValue);

            }

          }
          this.tableShow=flag;
          //笛卡尔积
        },calcDescartes:function(array) {
              if (array.length < 2) return array[0] || [];
              return [].reduce.call(array, function (col, set) {
                var res = [];
                col.forEach(function (c) {
                  set.forEach(function (s) {
                    var t = [].concat(Array.isArray(c) ? c : [c]);
                    t.push(s);
                    res.push(t);
                  })
                });
                return res;
              });
            }

        }
    }
</script>

<style scoped>

</style>
