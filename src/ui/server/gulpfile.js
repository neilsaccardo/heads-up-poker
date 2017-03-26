var gulp = require('gulp');
var browserSync = require('browser-sync').create();

var concat = require('gulp-concat');
var rename = require('gulp-rename');
var uglify = require('gulp-uglify');
var cleanCSS = require('gulp-clean-css');
var sourcemaps = require('gulp-sourcemaps');

var jsFiles = ['src/js/services/*.js', 'src/js/util/**/*.js', 'src/js/*.js'],
    jsDest = 'src/';
var jsScripts = 'scripts.js';
var jsMinScripts = 'scripts.min.js';

var cssFiles = ['src/styles/*.css'],
    cssDest = 'src';
var cssMinStyles = 'styles.min.css';

gulp.task('concatScripts', function() {
    return gulp.src(jsFiles)
        .pipe(concat(jsScripts))
        .pipe(gulp.dest(jsDest));
});

gulp.task('concatAndMinifyCSS', function() {
    return gulp.src(cssFiles)
        .pipe(rename(cssMinStyles))
        .pipe(sourcemaps.init())
        .pipe(cleanCSS({compatibility: 'ie8'}))
        .pipe(sourcemaps.write())
        .pipe(gulp.dest(cssDest));
});


gulp.task('minifyScripts', function() {
    return  gulp.src(jsDest + jsScripts)
           .pipe(rename(jsMinScripts))
           .pipe(uglify())
           .pipe(gulp.dest(jsDest));
});

