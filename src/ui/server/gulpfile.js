var gulp = require('gulp');
var browserSync = require('browser-sync').create();
var nodemon = require('nodemon');
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

gulp.task('watch', function() {
    gulp.watch(jsFiles, ['concatScripts']);
    gulp.watch(cssFiles, ['concatAndMinifyCSS']);
});

// start our server and listen for changes -
//http://stackoverflow.com/questions/28048029/running-a-command-with-gulp-to-start-node-js-server
gulp.task('server', function() {
    // configure nodemon
    nodemon({
        // the script to run the app
        script: 'app.js',
        // this listens to changes in any of these files/routes and restarts the application
        watch: ['app.js', 'db.js', jsScripts, cssMinStyles],
        ext: 'js'

    }).on('restart', function() {
        gulp.src('server.js')
  });
});

// default task allows node to be continually run, with changes being reloaded and watched.
gulp.task('default', ['server', 'watch']);