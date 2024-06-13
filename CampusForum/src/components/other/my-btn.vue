<template>
<button class="btn">
    <slot></slot>
</button>
</template>

<script lang="ts">

export default {
name: 'MyBtn' ,
}
</script>
<style scoped>
.btn{
    border: 3px solid #fff;
    background-color: #BAE3E1;
    width: 85px;
    height: 45px;
    font-size:20px;
    display:flex;
    align-items:center;
    letter-spacing: 2px;
    font-weight: 1000;
    color: #F4FAFA;
    justify-content: center;
    
}
.btn:hover{
    background-color: #76AFAE;

}
</style>