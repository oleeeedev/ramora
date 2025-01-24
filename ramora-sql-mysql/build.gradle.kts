dependencies {
    api(libs.hikari)

    implementation(libs.mysql)
    api(project(":ramora-common"))
    api(project(":ramora-sql-parent"))
}