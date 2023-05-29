using AutoMapper;
using Business.Dto;
using DataAccess.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Business.Mapper
{
    internal class AutoMapperProfile:Profile
    {
        public AutoMapperProfile()
        {
            CreateMap<Category, CategoryDto>();
            CreateMap<CategoryDto, Category>();
            CreateMap<Product, ProductDto>();
            CreateMap<ProductDto, Product>();
            CreateMap<ProductShopDto, ProductShop>();
            CreateMap<ProductShop, ProductShopDto>();
            CreateMap<ProductReview, ProductReviewDto>();
            CreateMap<ProductReviewDto, ProductReview>();
            CreateMap<ProductType, ProductTypeDto>();
            CreateMap<ProductTypeDto, ProductType>();
            CreateMap<Shop, ShopDto>();
            CreateMap<ShopDto, Shop>();
            CreateMap<ShoppingList, ShoppingListDto>();
            CreateMap<ShoppingListDto, ShoppingList>();
            CreateMap<User, UserDto>();
            CreateMap<UserDto, User>();
        }
    }
}
