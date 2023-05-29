using System;
using System.Collections.Generic;
using DataAccess.Models;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;

namespace DataAccess.Data;

public partial class CartCrafterDbContext : IdentityDbContext<User>
{
    public CartCrafterDbContext()
    {
    }

    public CartCrafterDbContext(DbContextOptions<CartCrafterDbContext> options)
        : base(options)
    {
    }

    public virtual DbSet<Brand> Brands { get; set; }

    public virtual DbSet<Category> Categories { get; set; }

    public virtual DbSet<Chain> Chains { get; set; }

    public virtual DbSet<Product> Products { get; set; }

    public virtual DbSet<ProductReview> ProductReviews { get; set; }

    public virtual DbSet<ProductShop> ProductShops { get; set; }

    public virtual DbSet<ProductType> ProductTypes { get; set; }

    public virtual DbSet<Shop> Shops { get; set; }

    public virtual DbSet<ShoppingList> ShoppingLists { get; set; }

    public virtual DbSet<ShoppingListProduct> ShoppingListProducts { get; set; }

    public virtual DbSet<User> Users { get; set; }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        base.OnModelCreating(modelBuilder);
        modelBuilder.Entity<Brand>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PK__Brand__3214EC275A00963D");

            entity.ToTable("Brand");

            entity.Property(e => e.Id).HasColumnName("ID");
            entity.Property(e => e.BrandName)
                .HasMaxLength(255)
                .IsUnicode(false);
            entity.Property(e => e.Logo).HasColumnType("image");
        });

        modelBuilder.Entity<Category>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PK__Category__3214EC27507CCB15");

            entity.ToTable("Category");

            entity.Property(e => e.Id).HasColumnName("ID");
            entity.Property(e => e.Description)
                .HasMaxLength(255)
                .IsUnicode(false);
            entity.Property(e => e.Image).HasColumnType("image");
            entity.Property(e => e.Name)
                .HasMaxLength(50)
                .IsUnicode(false);
            entity.Property(e => e.ParentId)
                .HasMaxLength(450)
                .HasColumnName("ParentID");

            entity.HasOne(d => d.Parent).WithMany(p => p.InverseParent)
                .HasForeignKey(d => d.ParentId)
                .HasConstraintName("FK_Categories_Parent");
        });

        modelBuilder.Entity<Chain>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PK__Chain__3214EC278267F6DD");

            entity.ToTable("Chain");

            entity.Property(e => e.Id).HasColumnName("ID");
            entity.Property(e => e.ChainName)
                .HasMaxLength(255)
                .IsUnicode(false);
        });

        modelBuilder.Entity<Product>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PK__Product__3214EC2775E0EB4F");

            entity.ToTable("Product");

            entity.Property(e => e.Id).HasColumnName("ID");
            entity.Property(e => e.Animal)
                .HasMaxLength(50)
                .IsUnicode(false);
            entity.Property(e => e.BarCode)
                .HasMaxLength(50)
                .IsUnicode(false);
            entity.Property(e => e.BrandId)
                .HasMaxLength(450)
                .IsUnicode(false)
                .HasColumnName("BrandID");
            entity.Property(e => e.Image).HasColumnType("image");
            entity.Property(e => e.ProductDescription)
                .HasMaxLength(255)
                .IsUnicode(false);
            entity.Property(e => e.ProductName)
                .HasMaxLength(50)
                .IsUnicode(false);
            entity.Property(e => e.TypeId)
                .HasMaxLength(450)
                .HasColumnName("TypeID");

            entity.HasOne(d => d.Type).WithMany(p => p.Products)
                .HasForeignKey(d => d.TypeId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK_Products_TypeID");
        });

        modelBuilder.Entity<ProductReview>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PK__ProductR__3214EC275C3CE033");

            entity.ToTable("ProductReview");

            entity.Property(e => e.Id).HasColumnName("ID");
            entity.Property(e => e.Image).HasColumnType("image");
            entity.Property(e => e.ProductId)
                .HasMaxLength(450)
                .HasColumnName("ProductID");
            entity.Property(e => e.Text)
                .HasMaxLength(255)
                .IsUnicode(false);
            entity.Property(e => e.UserId)
                .HasMaxLength(450)
                .HasColumnName("UserID");

            entity.HasOne(d => d.Product).WithMany(p => p.ProductReviews)
                .HasForeignKey(d => d.ProductId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK_Reviews_ProductID");

            entity.HasOne(d => d.User).WithMany(p => p.ProductReviews)
                .HasForeignKey(d => d.UserId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK_Reviews_UserID");
        });

        modelBuilder.Entity<ProductShop>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PK__ProductS__3214EC27E3CEB2FB");

            entity.ToTable("ProductShop");

            entity.Property(e => e.Id).HasColumnName("ID");
            entity.Property(e => e.DateAdded).HasColumnType("date");
            entity.Property(e => e.Price).HasColumnType("decimal(10, 2)");
            entity.Property(e => e.ProductId).HasMaxLength(450);
            entity.Property(e => e.ShopId).HasMaxLength(450);

            entity.HasOne(d => d.Product).WithMany(p => p.ProductShops)
                .HasForeignKey(d => d.ProductId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK_ProductShop_Product");

            entity.HasOne(d => d.Shop).WithMany(p => p.ProductShops)
                .HasForeignKey(d => d.ShopId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK_ProductShop_Shop");
        });

        modelBuilder.Entity<ProductType>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PK__ProductT__3214EC27F7742BAD");

            entity.ToTable("ProductType");

            entity.Property(e => e.Id).HasColumnName("ID");
            entity.Property(e => e.CategoryId)
                .HasMaxLength(450)
                .HasColumnName("CategoryID");
            entity.Property(e => e.Description)
                .HasMaxLength(255)
                .IsUnicode(false);
            entity.Property(e => e.Image).HasColumnType("image");
            entity.Property(e => e.Name)
                .HasMaxLength(50)
                .IsUnicode(false);

            entity.HasOne(d => d.Category).WithMany(p => p.ProductTypes)
                .HasForeignKey(d => d.CategoryId)
                .HasConstraintName("FK_Category_CategoryID");
        });

        modelBuilder.Entity<Shop>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PK__Shop__3214EC27DFB15DB0");

            entity.ToTable("Shop");

            entity.Property(e => e.Id).HasColumnName("ID");
            entity.Property(e => e.ChainId).HasMaxLength(450);
            entity.Property(e => e.Latitude).HasColumnName("latitude");
            entity.Property(e => e.Longitude).HasColumnName("longitude");
            entity.Property(e => e.ShopAddress)
                .HasMaxLength(100)
                .IsUnicode(false);
            entity.Property(e => e.ShopName)
                .HasMaxLength(50)
                .IsUnicode(false);

            entity.HasOne(d => d.Chain).WithMany(p => p.Shops)
                .HasForeignKey(d => d.ChainId)
                .HasConstraintName("FK_Shop_Chain");
        });

        modelBuilder.Entity<ShoppingList>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PK__Shopping__3214EC27CFC37745");

            entity.ToTable("ShoppingList");

            entity.Property(e => e.Id).HasColumnName("ID");
            entity.Property(e => e.DateCreated).HasColumnType("date");
            entity.Property(e => e.Name).HasMaxLength(100);
            entity.Property(e => e.UserId)
                .HasMaxLength(450)
                .HasColumnName("UserID");

            entity.HasOne(d => d.User).WithMany(p => p.ShoppingLists)
                .HasForeignKey(d => d.UserId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK_ShoppingList_UserID");
        });

        modelBuilder.Entity<ShoppingListProduct>(entity =>
        {
            entity.HasKey(e => new { e.ShoppingListId, e.ProductId });

            entity.ToTable("ShoppingListProduct");

            entity.Property(e => e.ShoppingListId).HasColumnName("ShoppingListID");
            entity.Property(e => e.ProductId).HasColumnName("ProductID");

            entity.HasOne(d => d.Product).WithMany(p => p.ShoppingListProducts)
                .HasForeignKey(d => d.ProductId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK_ShoppingListProduct_ProductID");

            entity.HasOne(d => d.ShoppingList).WithMany(p => p.ShoppingListProducts)
                .HasForeignKey(d => d.ShoppingListId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("FK_ShoppingListProduct_ShoppingListID");
        });

        modelBuilder.Entity<User>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PK__User__3214EC2753C9A37B");

            entity.ToTable("User");

            entity.Property(e => e.Id).HasColumnName("ID");
            entity.Property(e => e.Email)
                .HasMaxLength(255)
                .IsUnicode(false);
            entity.Property(e => e.UserName)
                .HasMaxLength(50)
                .IsUnicode(false);
        });

        OnModelCreatingPartial(modelBuilder);
    }

    partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
}
