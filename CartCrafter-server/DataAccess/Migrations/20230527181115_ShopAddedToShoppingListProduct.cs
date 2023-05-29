using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace DataAccess.Migrations
{
    /// <inheritdoc />
    public partial class ShopAddedToShoppingListProduct : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "ShopId",
                table: "ShoppingListProduct",
                type: "nvarchar(450)",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_ShoppingListProduct_ShopId",
                table: "ShoppingListProduct",
                column: "ShopId");

            migrationBuilder.AddForeignKey(
                name: "FK_ShoppingListProduct_Shop_ShopId",
                table: "ShoppingListProduct",
                column: "ShopId",
                principalTable: "Shop",
                principalColumn: "ID");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_ShoppingListProduct_Shop_ShopId",
                table: "ShoppingListProduct");

            migrationBuilder.DropIndex(
                name: "IX_ShoppingListProduct_ShopId",
                table: "ShoppingListProduct");

            migrationBuilder.DropColumn(
                name: "ShopId",
                table: "ShoppingListProduct");
        }
    }
}
